package com.bizmobiz.bizmoweb.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.bizmobiz.bizmoweb.domain.OfferWork;
import com.bizmobiz.bizmoweb.domain.OfferWorkState;
import com.bizmobiz.bizmoweb.domain.Proposal;
import com.bizmobiz.bizmoweb.domain.Setup;
import com.bizmobiz.bizmoweb.domain.User;
import com.bizmobiz.bizmoweb.repository.OfferJobRepository;
import com.bizmobiz.bizmoweb.repository.OfferWorkRepository;
import com.bizmobiz.bizmoweb.repository.OfferWorkStateRepository;
import com.bizmobiz.bizmoweb.repository.ProposalRepository;
import com.bizmobiz.bizmoweb.repository.SetupRepository;
import com.bizmobiz.bizmoweb.repository.UserRepository;
import com.bizmobiz.bizmoweb.value.OfferJobOfferDistanceValue;

@Component
public class JobScanOfferWork {

	private Logger log = LoggerFactory.getLogger(this.getClass());
    private SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    
    @Autowired
	private OfferJobRepository offerJobRepository;
	@Autowired
	private OfferWorkRepository offerWorkRepository;
	@Autowired
	private OfferWorkStateRepository offerWorkStateRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ProposalRepository proposalRepository;
	@Autowired
	private SetupRepository setupRepository;
	@Autowired
    private JavaMailSender sender;

    @Scheduled(fixedRate = 60000)
    public void scanOfferWork() {
        log.info("Scanning offer works at {}", dateFormat.format(new Date()));
        
        try {
        	OfferWorkState offerWorkStateSolicitado = offerWorkStateRepository.findOne(Constant.OFFER_WORK_STATE_SOLICITADO);
        	OfferWorkState offerWorkStatePropuesta = offerWorkStateRepository.findOne(Constant.OFFER_WORK_STATE_EN_PROPUESTA);
            
            List<OfferWork> offerWorks = offerWorkRepository.findAllByMinorWorkDateState(new Date(), offerWorkStateSolicitado);
            
            if (!offerWorks.isEmpty()) {
            	log.info("Se encontraron {} ofertas de trabajo [SOLICITADO] para procesar.", offerWorks.size());
            	
				for (Iterator<OfferWork> iterator = offerWorks.iterator(); iterator.hasNext();) {
					OfferWork offerWork = iterator.next();
					offerWork.setState(offerWorkStatePropuesta);
					offerWorkRepository.save(offerWork);
					
					log.info("Procesando oferta de trabajo: {}", offerWork.getId());
					
					Setup setup = setupRepository.findOneByCountryKey(offerWork.getClient().getCountry(), Constant.SETUP_KEY_SEARCH_RADIUS);
					
					List<OfferJobOfferDistanceValue> offerJobs = offerJobRepository.findAllByServicePositionRadiusCountry(offerWork.getService().getId(), offerWork.getPosition().getX(), offerWork.getPosition().getY(), Integer.valueOf(setup.getValue()), offerWork.getClient().getCountry().getId());
					
					if (!offerJobs.isEmpty()) {
						log.info("Se encontraron {} oferentes.", offerJobs.size());
						
						for (Iterator<OfferJobOfferDistanceValue> iterator2 = offerJobs.iterator(); iterator2.hasNext();) {
							OfferJobOfferDistanceValue offerJobOfferDistanceValue = iterator2.next();
							
							User offer = userRepository.findOne(offerJobOfferDistanceValue.getOfferId());
							
							log.info("Enviando email a oferente: {}", offer.getId());
							
							try {
								MimeMessage message = sender.createMimeMessage();
						        MimeMessageHelper helper = new MimeMessageHelper(message);
						        
						        helper.setTo(offer.getEmail());
						        helper.setSubject("Nueva oferta de trabajo para "+offerJobOfferDistanceValue.getTitle());
						        
						        StringBuffer htmlText = new StringBuffer();
						        htmlText.append("<html><body style=\"color:#000000;\">");
						        htmlText.append("<p>Estimad@ "+offer.getName()+":</p><br>");
						        htmlText.append("<p>Le informamos que se encuentra activa una nueva oferta de trabajo cercana a su posición a la cual podrá postular dentro de los próximos "+ Constant.OFFER_WORK_PROPOSAL_TIMEOUT +" minutos.</p><br>");
						        htmlText.append("<p><strong>Título de la oferta:</strong> "+offerWork.getTitle()+"</p>");
						        htmlText.append("<p><strong>Descripción de la oferta:</strong> "+offerWork.getDescription()+"</p><br><br>");
						        htmlText.append("<p>Saludos,</p>");
						        htmlText.append("<p style=\"color:blue;font-weight:bold;\">Equipo Bizmobiz</p>");
						        htmlText.append("</body></html>");
						        
						        helper.setText(htmlText.toString(), true);
						        
						        sender.send(message);
							} catch (Exception e) {
								log.error(e.getMessage(), e);
							}
						}
					} else {
						log.info("No se encontraron oferentes.");
					}
				}
			} else {
				log.info("No se encontraron ofertas de trabajo [SOLICITADO] para procesar.");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
    }
    
    @Scheduled(fixedRate = 30000)
    public void scanProposal() {
    	log.info("Scanning proposals at {}", dateFormat.format(new Date()));
    	
    	try {
    		OfferWorkState offerWorkStateContacto = offerWorkStateRepository.findOne(Constant.OFFER_WORK_STATE_EN_CONTACTO);
    		
    		List<OfferWork> offerWorks = offerWorkRepository.findAllByProposalTimeout(Constant.OFFER_WORK_PROPOSAL_TIMEOUT, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), Constant.OFFER_WORK_STATE_EN_PROPUESTA);
    		
    		if (!offerWorks.isEmpty()) {
            	log.info("Se encontraron {} ofertas de trabajo [EN PROPUESTA] para procesar.", offerWorks.size());
            	
				for (Iterator<OfferWork> iterator = offerWorks.iterator(); iterator.hasNext();) {
					OfferWork offerWork = iterator.next();
					offerWork.setState(offerWorkStateContacto);
					offerWorkRepository.save(offerWork);
					
					log.info("Procesando oferta de trabajo: {}", offerWork.getId());
					
					List<Proposal> proposals = proposalRepository.findByOfferWork(offerWork);
					User client = offerWork.getClient();
					
					log.info("Enviando email a cliente: {}", client.getId());
					
					MimeMessage message = sender.createMimeMessage();
			        MimeMessageHelper helper = new MimeMessageHelper(message);
			        
			        helper.setTo(client.getEmail());
			        helper.setSubject("Resultados de su oferta de trabajo");
			        
			        StringBuffer htmlText = new StringBuffer();
			        htmlText.append("<html><body style=\"color:#000000;\">");
			        htmlText.append("<p>Estimad@ "+client.getName()+":</p><br>");
					
					if (!proposals.isEmpty()) {
						log.info("Se encontraron "+ proposals.size() + " propuestas.");
						
						htmlText.append("<p>Le informamos que su oferta de trabajo \""+ offerWork.getTitle() +"\" recibió "+ proposals.size() +" propuestas de trabajo. A continuación le mostramos el detalle de estas:</p><br>");
						
						for (Iterator<Proposal> iterator2 = proposals.iterator(); iterator2.hasNext();) {
							Proposal proposal = iterator2.next();
							
							htmlText.append("<p><strong>Oferente:</strong> "+proposal.getOfferJob().getOfferName()+"</p>");
					        htmlText.append("<p><strong>Servicio oferente:</strong> "+proposal.getOfferJob().getTitle()+"</p>");
					        htmlText.append("<p><strong>Precio estimado:</strong> "+proposal.getCost()+"</p>");
					        htmlText.append("<p><strong>Comentarios:</strong> "+proposal.getComments()+"</p>");
					        htmlText.append("<p>-------------------------------</p>");
						}
						htmlText.append("<p>Le recordamos que para revisar las propuestas y contactarse con los oferentes debe ingresar nuevamente a Bizmobiz.</p><br>");
					} else {
						log.info("No se encontraron propuestas.");
						
						htmlText.append("<p>Le informamos que lamentablemente su oferta de trabajo titulada \""+ offerWork.getTitle() +"\" no recibió propuestas de trabajo.</p><br>");
					}
					
					htmlText.append("<br><p>Saludos,</p>");
			        htmlText.append("<p style=\"color:blue;font-weight:bold;\">Equipo Bizmobiz</p>");
					htmlText.append("</body></html>");
					helper.setText(htmlText.toString(), true);
			        
			        sender.send(message);
				}
			} else {
				log.info("No se encontraron ofertas de trabajo [EN PROPUESTA] para procesar.");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
    }
}
