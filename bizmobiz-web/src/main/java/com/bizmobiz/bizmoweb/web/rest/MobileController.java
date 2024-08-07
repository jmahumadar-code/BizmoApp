package com.bizmobiz.bizmoweb.web.rest;

import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bizmobiz.bizmoweb.domain.Country;
import com.bizmobiz.bizmoweb.domain.GeoTracker;
import com.bizmobiz.bizmoweb.domain.GeoTrackerType;
import com.bizmobiz.bizmoweb.domain.ImageWork;
import com.bizmobiz.bizmoweb.domain.LevelService;
import com.bizmobiz.bizmoweb.domain.OfferJob;
import com.bizmobiz.bizmoweb.domain.OfferWork;
import com.bizmobiz.bizmoweb.domain.OfferWorkState;
import com.bizmobiz.bizmoweb.domain.Proposal;
import com.bizmobiz.bizmoweb.domain.ProposalState;
import com.bizmobiz.bizmoweb.domain.Service;
import com.bizmobiz.bizmoweb.domain.Setup;
import com.bizmobiz.bizmoweb.domain.Tag;
import com.bizmobiz.bizmoweb.domain.User;
import com.bizmobiz.bizmoweb.repository.CountryRepository;
import com.bizmobiz.bizmoweb.repository.GeoTrackerRepository;
import com.bizmobiz.bizmoweb.repository.GeoTrackerTypeRepository;
import com.bizmobiz.bizmoweb.repository.ImageWorkRepository;
import com.bizmobiz.bizmoweb.repository.LevelServiceRepository;
import com.bizmobiz.bizmoweb.repository.OfferJobRepository;
import com.bizmobiz.bizmoweb.repository.OfferWorkRepository;
import com.bizmobiz.bizmoweb.repository.OfferWorkStateRepository;
import com.bizmobiz.bizmoweb.repository.ProposalRepository;
import com.bizmobiz.bizmoweb.repository.ProposalStateRepository;
import com.bizmobiz.bizmoweb.repository.ServiceRepository;
import com.bizmobiz.bizmoweb.repository.SetupRepository;
import com.bizmobiz.bizmoweb.repository.TagRepository;
import com.bizmobiz.bizmoweb.repository.UserRepository;
import com.bizmobiz.bizmoweb.s3.S3Wrapper;
import com.bizmobiz.bizmoweb.util.Constant;
import com.bizmobiz.bizmoweb.util.Validate;
import com.bizmobiz.bizmoweb.value.OfferJobDistanceValue;
import com.bizmobiz.bizmoweb.value.OfferWorkDistanceValue;
import com.bizmobiz.bizmoweb.value.ProposalBasicValue;
import com.bizmobiz.bizmoweb.value.ServiceBasic;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.AddressComponent;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;

@Controller
@RequestMapping(path = "/api/mobile")
public class MobileController {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private SetupRepository setupRepository;
	@Autowired
	private GeoTrackerRepository geoTrackerRepository;
	@Autowired
	private GeoTrackerTypeRepository geoTrackerTypeRepository;
	@Autowired
	private CountryRepository countryRepository;
	@Autowired
	private LevelServiceRepository levelServiceRepository;
	@Autowired
	private ServiceRepository serviceRepository;
	@Autowired
	private OfferJobRepository offerJobRepository;
	@Autowired
	private OfferWorkRepository offerWorkRepository;
	@Autowired
	private OfferWorkStateRepository offerWorkStateRepository;
	@Autowired
	private ProposalRepository proposalRepository;
	@Autowired
	private ProposalStateRepository proposalStateRepository;
	@Autowired
	private TagRepository tagRepository;
	@Autowired
	private ImageWorkRepository imageWorkRepository;
	@Autowired
    private JavaMailSender sender;
	@Autowired
	private S3Wrapper s3Wrapper;
	
	@Value("${aws.s3.bucket.offerWork}")
    private String bucketNameOfferWork;
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@GetMapping(path = "/userExist")
    public @ResponseBody Map<String, Object> userExist(
    		@RequestParam(name="rrss_email", required = true) String rrssEmail,
    		@RequestParam(name="rrss_uid", required = true) String rrssUid, 
    		@RequestParam(name="rrss_type", required = true) Integer rrssType) throws URISyntaxException, Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		
		User user = userRepository.findByEmail(rrssEmail);
		
		if (user == null) {
			log.info("User is null.");
			result.put("success", false);
			result.put("info", "No existe una cuenta asociada a su red social al mail de la RRSS.");
		} else if (rrssType == Constant.USER_RRSSUID_GOOGLE && user.getGoogleUid() == null) {
			log.info("User Google UID is null.");
			result.put("success", true);
			result.put("info", "El usuario existe y no tiene cuenta Google asociada.");
		} else if (rrssType == Constant.USER_RRSSUID_GOOGLE && user.getGoogleUid().equals(rrssUid)) {
			log.info("User Google UID == request UID.");
			result.put("success", true);
			result.put("info", "El usuario existe y la cuenta Google asociada es la misma consultada.");
		} else if (rrssType == Constant.USER_RRSSUID_GOOGLE && !user.getGoogleUid().equals(rrssUid)) {
			log.info("User Google UID <> request UID.");
			result.put("success", true);
			result.put("info", "El usuario existe y la cuenta Google asociada no es la misma consultada.");
		} else if (rrssType == Constant.USER_RRSSUID_FACEBOOK && user.getFacebookUid() == null) {
			log.info("User Facebook UID is null.");
			result.put("success", true);
			result.put("info", "El usuario existe y no tiene cuenta Facebook asociada.");
		} else if (rrssType == Constant.USER_RRSSUID_FACEBOOK && user.getFacebookUid().equals(rrssUid)) {
			log.info("User Facebook UID == request UID.");
			result.put("success", true);
			result.put("info", "El usuario existe y la cuenta Facebook asociada es la misma consultada.");
		} else if (rrssType == Constant.USER_RRSSUID_FACEBOOK && !user.getFacebookUid().equals(rrssUid)) {
			log.info("User Facebook UID <> request UID.");
			result.put("success", true);
			result.put("info", "El usuario existe y la cuenta Facebook asociada no es la misma consultada.");
		} else {
			log.info("Condición no soportada.");
			result.put("success", false);
			result.put("info", "Condición no soportada.");
		}
		
		return result;
	}

	@GetMapping(path = "/login")
    public @ResponseBody Map<String, Object> login(
    		@RequestParam(name="email", required = true) String email, 
    		@RequestParam(name="password", required = true) String password, 
    		@RequestParam(name="setup_version", required = true) String setupVersion,
    		@RequestParam(name="country_code", required = true) String country_code,
    		@RequestParam(name="latitud", required = true) Double latitud,
    		@RequestParam(name="longitud", required = true) Double longitud) throws URISyntaxException, Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		
		try {
			Validate validCountry = validateCountry(latitud, longitud, country_code);
			
			if (validCountry.getValid()) {
				User user = userRepository.findByEmailAndPassword(email, password);
				
				if (user == null) {
					log.info("User is null.");
					result.put("success", false);
					result.put("info", "E-mail o Password ingresados son incorrectos.");
					return result;
				}
				
				if (user.getIsEnabled() == Boolean.FALSE) {
					log.info("User disabled.");
					result.put("success", false);
					result.put("info", "El usuario asociado a su cuenta se encuentra inactivo.");
					return result;
				}
				
				result.put("success", true);
				result.put("info", "Login exitoso.");
				result.put("user_data", getUserData(user, setupVersion));
				
				insertTracking(user.getId(), latitud, longitud, Constant.GEO_TRACKER_TYPE_LOGIN);
			} else {
				result.put("success", false);
				result.put("info", validCountry.getMessage());
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			result.put("success", false);
			result.put("info", "Ocurrió un error al obtener su cuenta de usuario.");
		}
		return result;
    }
	
	@GetMapping(path = "/loginRRSS")
    public @ResponseBody Map<String, Object> loginRRSS(
    		@RequestParam(name="rrss_email", required = true) String rrssEmail, 
    		@RequestParam(name="rrss_uid", required = true) String rrssUid, 
    		@RequestParam(name="rrss_type", required = true) Integer rrssType, 
    		@RequestParam(name="setup_version", required = true) String setupVersion,
    		@RequestParam(name="country_code", required = true) String country_code,
    		@RequestParam(name="latitud", required = true) Double latitud,
    		@RequestParam(name="longitud", required = true) Double longitud) throws URISyntaxException, Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		
		try {
			Validate validCountry = validateCountry(latitud, longitud, country_code);
			
			if (validCountry.getValid()) {
				User user = userRepository.findByEmail(rrssEmail);
				
				if (user == null) {
					log.info("User is null.");
					result.put("success", false);
					result.put("info", "No existe una cuenta asociada a su red social.");
				} else if (user.getIsEnabled() == Boolean.FALSE) {
					log.info("User disabled.");
					result.put("success", false);
					result.put("info", "El usuario asociado a su cuenta se encuentra inactivo.");
				} else {
					if (rrssType != Constant.USER_RRSSUID_GOOGLE && rrssType != Constant.USER_RRSSUID_FACEBOOK) {
						log.info("rrssType [" + rrssType +"] is not defined.");
						result.put("success", false);
						result.put("info", "No se pudo obtener la cuenta asociada a su red social.");
					} else if (rrssType == Constant.USER_RRSSUID_GOOGLE && user.getGoogleUid() != null && 
							!user.getGoogleUid().equals(rrssUid)) {
						log.info("User Google UID ["+user.getGoogleUid()+"] != request Google UID ["+rrssUid+"].");
						result.put("success", false);
						result.put("info", "El inicio de sesión a través de su cuenta de Google ha fallado.");
					} else if (rrssType == Constant.USER_RRSSUID_FACEBOOK && user.getFacebookUid() != null && 
							!user.getFacebookUid().equals(rrssUid)) {
						log.info("User Facebook UID ["+user.getGoogleUid()+"] != request Facebook UID ["+rrssUid+"].");
						result.put("success", false);
						result.put("info", "El inicio de sesión a través de su cuenta de Facebook ha fallado.");
					} else if ((rrssType == Constant.USER_RRSSUID_FACEBOOK && ((user.getFacebookUid() != null && 
							user.getFacebookUid().equals(rrssUid)) ||  user.getFacebookUid() == null)) || 
							(rrssType == Constant.USER_RRSSUID_GOOGLE && ((user.getGoogleUid() != null && 
							user.getGoogleUid().equals(rrssUid)) ||  user.getGoogleUid() == null))) {
						if (rrssType == Constant.USER_RRSSUID_GOOGLE && user.getGoogleUid() == null) {
							log.info("Setting Google UID to user.");
							user.setGoogleUid(rrssUid);
							userRepository.save(user);
						} else if (rrssType == Constant.USER_RRSSUID_FACEBOOK && user.getFacebookUid() == null) {
							log.info("Setting Facebook UID to user.");
							user.setFacebookUid(rrssUid);
							userRepository.save(user);
						}
						
						result.put("success", true);
						result.put("info", "Login exitoso.");
						result.put("user_data", getUserData(user, setupVersion));
						
						insertTracking(user.getId(), latitud, longitud, Constant.GEO_TRACKER_TYPE_LOGIN);
					} else {
						throw new Exception("No se cumplió ninguna de las condiciones definidas para login con RRSS.");
					}
				}
			} else {
				result.put("success", false);
				result.put("info", validCountry.getMessage());
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			result.put("success", false);
			result.put("info", "Ocurrió un error al obtener la cuenta asociada a su red social.");
		}
		
		return result;
    }
	
	@GetMapping(path = "/setup")
    public @ResponseBody Map<String, String> setup(
    		@RequestParam(name="iduser", required = true) Integer userid) throws URISyntaxException, Exception {
		Map<String, String> setupValues = new HashMap<>();
		
		User user = userRepository.findOne(userid);
		List<Setup> setupList = setupRepository.findByCountry(user.getCountry());
		
		setupValues.put("version_setup", user.getCountry().getSetupVersion());
		
		for (Setup setup : setupList) {
			setupValues.put(setup.getKey(), setup.getValue());
		}
		
		return setupValues;
	}
	
	@GetMapping(path = "/tracking")
    public @ResponseBody Map<String, Object> tracking(
    		@RequestParam(name="iduser", required = true) Integer userid,
    		@RequestParam(name="latitud", required = true) Double latitud,
    		@RequestParam(name="longitud", required = true) Double longitud) throws URISyntaxException, Exception {
		Map<String, Object> trackingValues = new HashMap<>();
		
		try {
			Integer trackingResult = insertTracking(userid, latitud, longitud, Constant.GEO_TRACKER_TYPE_TRACKING);
			
			if (trackingResult != null) {
				trackingValues.put("result", true);
				trackingValues.put("info", "Tracking exitoso.");
				
				User user = userRepository.findOne(userid);
				trackingValues.put("user_data", getUserData(user, null));
			} else {
				trackingValues.put("result", false);
				trackingValues.put("info", "Tracking error.");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			trackingValues.put("result", false);
			trackingValues.put("info", "Ocurrió un error al realizar tracking.");
		}
		
		return trackingValues;
	}
	
	@GetMapping(path = "/register")
    public @ResponseBody Map<String, Object> register(
    		@RequestParam(name="name", required = true) String name,
    		@RequestParam(name="lastname", required = true) String lastname,
    		@RequestParam(name="country_code", required = true) String countryCode,
    		@RequestParam(name="email", required = true) String email,
    		@RequestParam(name="password", required = true) String password,
    		@RequestParam(name="phone_number", required = true) String phoneNumber,
    		@RequestParam(name="latitud", required = true) Double latitud,
    		@RequestParam(name="longitud", required = true) Double longitud,
    		@RequestParam(name="rrss_uid", required = false) String rrssUid, 
    		@RequestParam(name="rrss_type", required = false) Integer rrssType) throws URISyntaxException, Exception {
		
		Map<String, Object> result = new HashMap<>();
		
		try {
			Validate validCountry = validateCountry(latitud, longitud, countryCode);
			
			if (validCountry.getValid()) {
				Country country = countryRepository.findOneByCode(countryCode);
				
				User user = userRepository.findByEmail(email);
				
				if (user != null) {
					log.info("User already exist.");
					result.put("success", false);
					result.put("info", "El email ["+email+"] ya está siendo utilizado por otro usuario.");
					return result;
				}
				
				User newUser = new User();
				newUser.setPhoneNumber(phoneNumber);
				newUser.setEmail(email);
				newUser.setPassword(password);
				newUser.setName(name);
				newUser.setLastname(lastname);
				newUser.setCountry(country);
				newUser.setIsCompany(Boolean.FALSE);
				newUser.setIsOffer(Boolean.FALSE);
				newUser.setIsEnabled(Boolean.TRUE);
				
				if (rrssUid != null && rrssType != null) {
					if (rrssType == Constant.USER_RRSSUID_GOOGLE) {
						newUser.setGoogleUid(rrssUid);
					} else if (rrssType == Constant.USER_RRSSUID_FACEBOOK) {
						newUser.setFacebookUid(rrssUid);
					}
				}
				
				userRepository.save(newUser);
				
				if (newUser.getId() != null) {
					log.info("User register success.");
					result.put("success", true);
					result.put("info", "Registro de usuario exitoso.");
					result.put("user_data", getUserData(newUser, null));
					
					//Se guarda la posición de registro
					insertTracking(newUser.getId(), latitud, longitud, Constant.GEO_TRACKER_TYPE_REGISTER);
				} else {
					log.info("User register failed.");
					result.put("success", false);
					result.put("info", "Registro de usuario erróneo.");
				}
			} else {
				result.put("success", false);
				result.put("info", validCountry.getMessage());
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			result.put("success", false);
			result.put("info", "Ocurrió un error inesperado al registrar usuario.");
			return result;
		}
		
		return result;
	}
	
	@GetMapping(path = "/verifyCode")
    public @ResponseBody Map<String, Object> verifyCode(
    		@RequestParam(name="email", required = true) String email) throws URISyntaxException, Exception {
		Map<String, Object> verifyValues = new HashMap<>();
		
		StringBuffer randomPIN = new StringBuffer();
		Random r = new Random();
		
		while (randomPIN.length() < 4) {
			randomPIN.append(r.nextInt(9));
		}
		
		if (email != null) {
			log.info("Generating code by email: "+email);
			
			try {
				MimeMessage message = sender.createMimeMessage();
		        MimeMessageHelper helper = new MimeMessageHelper(message);
		        
		        helper.setTo(email);
		        helper.setText("El código de activación es: "+randomPIN.toString());
		        helper.setSubject("Código de activación");
		        
		        sender.send(message);
			} catch (Exception e) {
				verifyValues.put("result", false);
				verifyValues.put("info", "Ha ocurrido un error al enviar mensaje a su correo electrónico, inténtelo nuevamente.");
				
				return verifyValues;
			}
			
			verifyValues.put("result", true);
			verifyValues.put("info", "Se ha enviado un mensaje a su correo electrónico con el código de verificación.");
			verifyValues.put("verify_code", randomPIN.toString());
		} else {
			log.info("mail sended are null");
			verifyValues.put("result", false);
			verifyValues.put("info", "Ha ocurrido un error al recibir los datos de su cuenta, inténtelo nuevamente.");
		}
		
		return verifyValues;
	}
	
	@GetMapping(path = "/verifyPhone")
    public @ResponseBody Map<String, Object> verifyPhone(
    		@RequestParam(name="phone_number", required = true) String phoneNumber) throws URISyntaxException, Exception {
		Map<String, Object> verifyValues = new HashMap<>();
		
		try {
			List<User> user = userRepository.findByPhoneNumber(phoneNumber);
			
			if (user.size() == 0) {
				log.info("User doesn't exist.");
				verifyValues.put("success", true);
				verifyValues.put("info", "El número ["+phoneNumber+"] no existe en la base de datos de usuarios.");
			} else {
				log.info("User already exist.");
				verifyValues.put("success", false);
				verifyValues.put("info", "El número ["+phoneNumber+"] ya está siendo utilizado por ["+user.size()+"] usuario(s).");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return verifyValues;
	}
	
	@GetMapping(path = "/verifyEmail")
    public @ResponseBody Map<String, Object> verifyEmail(
    		@RequestParam(name="email", required = true) String email) throws URISyntaxException, Exception {
		Map<String, Object> verifyValues = new HashMap<>();
		
		try {
			User user = userRepository.findByEmail(email);
			
			if (user == null) {
				log.info("User doesn't exist.");
				verifyValues.put("success", true);
				verifyValues.put("info", "El email ["+email+"] no existe en la base de datos de usuarios.");
			} else {
				log.info("User already exist.");
				verifyValues.put("success", false);
				verifyValues.put("info", "El email ["+email+"] ya está siendo utilizado por otro usuario.");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return verifyValues;
	}
	
	@GetMapping(path = "/category")
    public @ResponseBody Map<String, Object> category() throws URISyntaxException, Exception {
		Map<String, Object> categories = new HashMap<>();
		
		LevelService levelService = levelServiceRepository.findOneByLevel(Constant.LEVEL_SERVICE_FIRST_LEVEL);
		List<Service> serviceList = serviceRepository.findByLevelService(levelService);
		
		categories.put("categories", serviceList);
		
		return categories;
	}
	
	@GetMapping(path = "/category/findByTag")
    public @ResponseBody Map<String, Object> categoryFindByTag(
    		@RequestParam(name="tag_value", required = true) String tagValue) throws URISyntaxException, Exception {
		Map<String, Object> categories = new HashMap<>();
		
		List<Tag> tagList = tagRepository.findByLikeTagValue(tagValue);
		List<Integer> tagIds = new ArrayList<Integer>();
		
		for (Iterator<Tag> iterator = tagList.iterator(); iterator.hasNext();) {
			Integer id = iterator.next().getId();
			log.info("iterator id: "+id);
			tagIds.add(id);
		}
		
		List<ServiceBasic> serviceList = (tagIds.size() >0)? serviceRepository.findByTagIds(tagIds): new ArrayList<>();
		
		categories.put("categories", serviceList);
		
		return categories;
	}
	
	@GetMapping(path = "/offer_job/initial")
    public @ResponseBody Map<String, Object> initialOffers(
    		@RequestParam(name="user_id", required = true) Integer user_id,
    		@RequestParam(name="latitud", required = true) Double latitud,
    		@RequestParam(name="longitud", required = true) Double longitud,
    		@RequestParam(name="radius", required = true) Integer radius) throws URISyntaxException, Exception {
		Map<String, Object> offers = new HashMap<>();
		
		try {
			User user = userRepository.findOne(user_id);
			
			if (user == null) {
				log.info("User doesn't exist.");
				offers.put("success", false);
				offers.put("info", "No se pudo obtener el usuario.");
			} else {
				log.info("User already exist.");
				
				List<OfferJobDistanceValue> offerList = offerJobRepository.find10ByPositionRadiusCountry(latitud, longitud, radius, user.getCountry().getId());
				
				offers.put("success", true);
				offers.put("offer_jobs", offerList);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			offers.put("success", false);
			offers.put("info", "Ocurrió un error al obtener los oferentes.");
		}
		
		return offers;
	}
	
	@GetMapping(path = "/updateToken")
    public @ResponseBody Map<String, Object> updateToken(
    		@RequestParam(name="user_id", required = true) Integer userId,
    		@RequestParam(name="token", required = true) String token) throws URISyntaxException, Exception {
		Map<String, Object> result = new HashMap<>();
		
		try {
			User user = userRepository.findOne(userId);
			
			if (user == null) {
				log.info("User doesn't exist.");
				result.put("success", false);
				result.put("info", "El usuario no existe en la base de datos.");
			} else {
				log.info("User already exist.");
				
				user.setUid(token);
				userRepository.save(user);
				
				result.put("success", true);
				result.put("info", "Token actualizado.");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			result.put("success", false);
			result.put("info", "Ocurrió un error al actualizar token de usuario.");
		}
		
		return result;
	}
	
	@PostMapping(path = "/offer_work/insert")
    public @ResponseBody Map<String, Object> insertOfferWork(HttpServletRequest request) throws URISyntaxException, Exception {
Map<String, Object> result = new HashMap<>();
		
		try {
			if (request.getParameter("user_id") == null) {
				throw new Exception("Param user_id can't be null.");
			}
			if (request.getParameter("title") == null) {
				throw new Exception("Param title can't be null.");
			}
			if (request.getParameter("description") == null) {
				throw new Exception("Param description can't be null.");
			}
			if (request.getParameter("latitud") == null) {
				throw new Exception("Param latitud can't be null.");
			}
			if (request.getParameter("longitud") == null) {
				throw new Exception("Param longitud can't be null.");
			}
			if (request.getParameter("type") == null) {
				throw new Exception("Param type can't be null.");
			}
			if ((Integer.valueOf(request.getParameter("type")) == Constant.OFFER_WORK_PROGRAMADA) && request.getParameter("work_date") == null) {
				throw new Exception("Param work_date can't be null.");
			}
			if (request.getParameter("service_id") == null) {
				throw new Exception("Param service_id can't be null.");
			}
			
			Integer userId = Integer.valueOf(request.getParameter("user_id"));
			String title = request.getParameter("title");
			String description = request.getParameter("description");
			Double latitud = Double.valueOf(request.getParameter("latitud"));
			Double longitud = Double.valueOf(request.getParameter("longitud"));
			Integer type = Integer.valueOf(request.getParameter("type"));
			
			Integer serviceId = Integer.valueOf(request.getParameter("service_id"));
			
			User client = userRepository.findOne(userId);
			Service service = serviceRepository.findOne(serviceId);
			Date createDate = new Date();
			Date workDate;
			OfferWorkState offerWorkState = offerWorkStateRepository.findOne(Constant.OFFER_WORK_STATE_SOLICITADO);
			
			OfferWork offerWork = new OfferWork();
			offerWork.setTitle(title);
			offerWork.setDescription(description);
			offerWork.setCreateDate(createDate);
			
			if (type == Constant.OFFER_WORK_PROGRAMADA) {
				workDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(request.getParameter("work_date"));
			} else {
				workDate = createDate;
			}
			offerWork.setWorkDate(workDate);
			
			offerWork.setPosition(new GeometryFactory().createPoint(new Coordinate(latitud, longitud)));
			offerWork.setClient(client);
			offerWork.setService(service);
			offerWork.setState(offerWorkState);
			
			log.info(offerWork.toString());
			offerWorkRepository.save(offerWork);
			
			if (offerWork.getId() != null) {
				log.info("OfferWork insert success.");
				result.put("success", true);
				result.put("info", "Oferta de trabajo ingresada exitosamente.");
				result.put("offer_work_id", offerWork.getId());
				
				log.info("INICIO PROCESO ALMACENAMIENTO IMAGENES");
				//Proceso de almacenamiento de imagenes
				try {
					Setup setup = setupRepository.findOneByCountryKey(client.getCountry(), Constant.SETUP_KEY_OFFER_WORK_IMAGE);
					int maxImages = (setup == null) ? Constant.SETUP_KEY_OFFER_WORK_IMAGE_DEFAULT : Integer.parseInt(setup.getValue());
										
					for (int i = 0; i < maxImages; i++) {
						String param = "image"+(i+1);
						
						if (request.getPart(param) != null) {
							log.info("Encontro param image"+(i+1));
							Part filePart = request.getPart(param);
							String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.
						    String uploadKey = bucketNameOfferWork+offerWork.getId()+"/"+fileName;
						    InputStream fileContent = filePart.getInputStream();
						    
						    s3Wrapper.upload(fileContent, uploadKey);
						    
						    ImageWork image = new ImageWork();
						    image.setPath(s3Wrapper.getFileUrl(uploadKey));
						    image.setOfferWork(offerWork);
						    
						    imageWorkRepository.save(image);
						    
						    if (image.getId() != null) {
						    	log.info("ImageWork insert ok.");
						    } else {
						    	log.info("ImageWork insert error.");
						    }
						}
					}
				} catch (Exception e) {
					log.info("Error al almacenar imagenes de oferta de trabajo.");
					log.error(e.getMessage(), e);
				}
				log.info("FIN PROCESO ALMACENAMIENTO IMAGENES");
			} else {
				log.info("OfferWork insert error.");
				result.put("success", false);
				result.put("info", "No se pudo ingresar la oferta de trabajo.");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			result.put("success", false);
			result.put("info", "Ocurrió un error al ingresar oferta de trabajo.");
		}
		
		return result;
	}
	
	@PostMapping(path = "/offer_work/insert2")
    public @ResponseBody Map<String, Object> insertOfferWork2(HttpServletRequest request) throws URISyntaxException, Exception {
		Map<String, Object> result = new HashMap<>();
		
		try {
			if (request.getParameter("user_id") == null) {
				throw new Exception("Param user_id can't be null.");
			}
			if (request.getParameter("title") == null) {
				throw new Exception("Param title can't be null.");
			}
			if (request.getParameter("description") == null) {
				throw new Exception("Param description can't be null.");
			}
			if (request.getParameter("latitud") == null) {
				throw new Exception("Param latitud can't be null.");
			}
			if (request.getParameter("longitud") == null) {
				throw new Exception("Param longitud can't be null.");
			}
			if (request.getParameter("type") == null) {
				throw new Exception("Param type can't be null.");
			}
			if ((Integer.valueOf(request.getParameter("type")) == Constant.OFFER_WORK_PROGRAMADA) && request.getParameter("work_date") == null) {
				throw new Exception("Param work_date can't be null.");
			}
			if (request.getParameter("service_id") == null) {
				throw new Exception("Param service_id can't be null.");
			}
			
			Integer userId = Integer.valueOf(request.getParameter("user_id"));
			String title = request.getParameter("title");
			String description = request.getParameter("description");
			Double latitud = Double.valueOf(request.getParameter("latitud"));
			Double longitud = Double.valueOf(request.getParameter("longitud"));
			Integer type = Integer.valueOf(request.getParameter("type"));
			
			Integer serviceId = Integer.valueOf(request.getParameter("service_id"));
			
			User client = userRepository.findOne(userId);
			Service service = serviceRepository.findOne(serviceId);
			Date createDate = new Date();
			Date workDate;
			OfferWorkState offerWorkState = offerWorkStateRepository.findOne(Constant.OFFER_WORK_STATE_SOLICITADO);
			
			OfferWork offerWork = new OfferWork();
			offerWork.setTitle(title);
			offerWork.setDescription(description);
			offerWork.setCreateDate(createDate);
			
			if (type == Constant.OFFER_WORK_PROGRAMADA) {
				workDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(request.getParameter("work_date"));
			} else {
				workDate = createDate;
			}
			offerWork.setWorkDate(workDate);
			
			offerWork.setPosition(new GeometryFactory().createPoint(new Coordinate(latitud, longitud)));
			offerWork.setClient(client);
			offerWork.setService(service);
			offerWork.setState(offerWorkState);
			
			log.info(offerWork.toString());
			offerWorkRepository.save(offerWork);
			
			if (offerWork.getId() != null) {
				log.info("OfferWork insert success.");
				result.put("success", true);
				result.put("info", "Oferta de trabajo ingresada exitosamente.");
				result.put("offer_work_id", offerWork.getId());
				
				log.info("INICIO PROCESO ALMACENAMIENTO IMAGENES");
				//Proceso de almacenamiento de imagenes
				try {
					Setup setup = setupRepository.findOneByCountryKey(client.getCountry(), Constant.SETUP_KEY_OFFER_WORK_IMAGE);
					int maxImages = (setup == null) ? Constant.SETUP_KEY_OFFER_WORK_IMAGE_DEFAULT : Integer.parseInt(setup.getValue());
										
					for (int i = 0; i < maxImages; i++) {
						String param = "image"+(i+1);
						
						if (request.getPart(param) != null) {
							log.info("Encontro param image"+(i+1));
							Part filePart = request.getPart(param);
							String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.
						    String uploadKey = bucketNameOfferWork+offerWork.getId()+"/"+fileName;
						    InputStream fileContent = filePart.getInputStream();
						    
						    s3Wrapper.upload(fileContent, uploadKey);
						    
						    ImageWork image = new ImageWork();
						    image.setPath(s3Wrapper.getFileUrl(uploadKey));
						    image.setOfferWork(offerWork);
						    
						    imageWorkRepository.save(image);
						    
						    if (image.getId() != null) {
						    	log.info("ImageWork insert ok.");
						    } else {
						    	log.info("ImageWork insert error.");
						    }
						}
					}
				} catch (Exception e) {
					log.info("Error al almacenar imagenes de oferta de trabajo.");
					log.error(e.getMessage(), e);
				}
				log.info("FIN PROCESO ALMACENAMIENTO IMAGENES");
			} else {
				log.info("OfferWork insert error.");
				result.put("success", false);
				result.put("info", "No se pudo ingresar la oferta de trabajo.");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			result.put("success", false);
			result.put("info", "Ocurrió un error al ingresar oferta de trabajo.");
		}
		
		return result;
	}
	
	@GetMapping(path = "/offerWorks")
    public @ResponseBody Map<String, Object> offerWorks(
    		@RequestParam(name="user_id", required = true) Integer user_id,
    		@RequestParam(name="latitud", required = true) Double latitud,
    		@RequestParam(name="longitud", required = true) Double longitud) throws URISyntaxException, Exception {
		Map<String, Object> offers = new HashMap<>();
		
		try {
			User user = userRepository.findOne(user_id);
			
			if (user == null) {
				log.info("User doesn't exist.");
				offers.put("success", false);
				offers.put("info", "No se pudo obtener el usuario.");
			} else {
				log.info("User exist.");
				
				Setup setup = setupRepository.findOneByCountryKey(user.getCountry(), Constant.SETUP_KEY_SEARCH_RADIUS);
				OfferJob offerJob = offerJobRepository.findOneByOffer(user);
				
				List<OfferWorkDistanceValue> offerList = offerWorkRepository.findAllInProposal(latitud, longitud, Integer.valueOf(setup.getValue()), user.getCountry().getId(), offerJob.getService().getId(), Constant.OFFER_WORK_STATE_EN_PROPUESTA);
				
				for (Iterator<OfferWorkDistanceValue> iterator = offerList.iterator(); iterator.hasNext();) {
					OfferWorkDistanceValue offerWorkDistanceValue = iterator.next();
					
					OfferWork offerWork = offerWorkRepository.findOne(offerWorkDistanceValue.getId());
					
					List<ImageWork> imageWorkList = imageWorkRepository.findByOfferWork(offerWork);
					Map<String, Object> imageValues = new HashMap<>();
					int countImage = 0;
					for (Iterator<ImageWork> iterator2 = imageWorkList.iterator(); iterator2.hasNext();) {
						ImageWork imageWork = iterator2.next();
						imageValues.put(String.valueOf(countImage++), imageWork.getPath());
					}
					
					offerWorkDistanceValue.setImages(imageValues);
				}
				
				offers.put("success", true);
				offers.put("offer_jobs", offerList);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			offers.put("success", false);
			offers.put("info", "Ocurrió un error al obtener los oferentes.");
		}
		
		return offers;
	}
	
	@GetMapping(path = "/offer_job/insert")
    public @ResponseBody Map<String, Object> offer_job_insert(
    		@RequestParam(name="title", required = true) String title,
    		@RequestParam(name="description", required = true) String description,
    		@RequestParam(name="offer_id", required = true) Integer offer_id,
    		@RequestParam(name="service_id", required = true) Integer service_id,
    		@RequestParam(name="latitud", required = true) Double latitud,
    		@RequestParam(name="longitud", required = true) Double longitud) throws URISyntaxException, Exception {
		
		Map<String, Object> result = new HashMap<>();
		
		try {
			Date createDate = new Date();
			Point position = new GeometryFactory().createPoint(new Coordinate(latitud, longitud));
			User offer = userRepository.findOne(offer_id);
			Service service = serviceRepository.findOne(service_id);
			
			OfferJob offerJob = new OfferJob();
			offerJob.setTitle(title);
			offerJob.setDescription(description);
			offerJob.setCreateDate(createDate);
			offerJob.setLastPosition(position);
			offerJob.setOffer(offer);
			offerJob.setService(service);
			
			offerJobRepository.save(offerJob);
			
			if (offerJob.getId() != null) {
				log.info("OfferJob insert success.");
				result.put("success", true);
				result.put("info", "Inserción exitosa.");
				result.put("offer_job_id", offerJob.getId());
			} else {
				result.put("success", false);
				result.put("info", "Ocurrió un error al ingresar el trabajo.");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			result.put("success", false);
			result.put("info", "Ocurrió un error inesperado al insertar datos.");
			return result;
		}
		
		return result;
	}
	
	@GetMapping(path = "/proposal/insert")
    public @ResponseBody Map<String, Object> proposal_insert(
    		@RequestParam(name="cost", required = false) Double cost,
    		@RequestParam(name="cost_min", required = false) Double costMin,
    		@RequestParam(name="cost_max", required = false) Double costMax,
    		@RequestParam(name="type_cost", required = true) Integer costType,
    		@RequestParam(name="comments", required = true) String comments,
    		@RequestParam(name="date", required = true) String createDateString,
    		@RequestParam(name="offer_work_id", required = true) Integer offer_work_id,
    		@RequestParam(name="offer_job_id", required = true) Integer offer_job_id) throws URISyntaxException, Exception {
		
		Map<String, Object> result = new HashMap<>();
		
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");			
			Date createDate = sdf.parse(createDateString);
			
			Proposal proposal = new Proposal();
			
			if (costType == Constant.PROPOSAL_COST_TYPE_UNICO && cost != null) {
				proposal.setCost(cost);
				proposal.setCostType(Boolean.FALSE);
			} else {
				log.info("cost is null.");
				result.put("success", false);
				result.put("info", "Ocurrió un error al ingresar la propuesta.");
			}
			
			if (costType == Constant.PROPOSAL_COST_TYPE_RANGO && costMin != null & costMax != null) {
				proposal.setCostMin(costMin);
				proposal.setCostMax(costMax);
				proposal.setCostType(Boolean.TRUE);
			} else {
				log.info("costMin and/or costMax are null.");
				result.put("success", false);
				result.put("info", "Ocurrió un error al ingresar la propuesta.");
			}
			
			proposal.setComments(comments);
			proposal.setDate(createDate);
			
			OfferJob offerJob = offerJobRepository.findOne(offer_job_id);
			OfferWork offerWork = offerWorkRepository.findOne(offer_work_id);
			ProposalState proposalState = proposalStateRepository.findOne(Constant.PROPOSAL_STATE_ENVIADA);
			
			proposal.setOfferJob(offerJob);
			proposal.setOfferWork(offerWork);
			proposal.setState(proposalState);
			
			proposalRepository.save(proposal);
			
			if (proposal.getId() != null) {
				log.info("Proposal insert success.");
				result.put("success", true);
				result.put("info", "Propuesta enviada exitosamente.");
				result.put("proposal_id", proposal.getId());
				result.put("user_data", getUserData(proposal.getOfferJob().getOffer(), null));
			} else {
				log.info("Proposal insert error.");
				result.put("success", false);
				result.put("info", "Ocurrió un error al ingresar la propuesta.");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			result.put("success", false);
			result.put("info", "Ocurrió un error inesperado al ingresar la propuesta.");
			return result;
		}
		
		return result;
	}
	
	@GetMapping(path = "/proposal")
    public @ResponseBody Map<String, Object> proposal(
    		@RequestParam(name="offer_work_id", required = true) Integer offer_work_id) throws URISyntaxException, Exception {
		Map<String, Object> result = new HashMap<>();
		
		try {
			OfferWork offerWork = offerWorkRepository.findOne(offer_work_id);
			
			if (offerWork == null) {
				log.info("OfferWork doesn't exist.");
				result.put("success", false);
				result.put("info", "No se pudo obtener la oferta de trabajo.");
			} else {
				log.info("OfferWork exist.");
				
				List<ProposalBasicValue> proposalList = proposalRepository.findByOfferWorkNative(offerWork.getId());
				
				result.put("success", true);
				result.put("proposals", proposalList);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			result.put("success", false);
			result.put("info", "Ocurrió un error al obtener los propuestas de trabajo.");
		}
		
		return result;
	}
	
	private Validate validateCountry(Double latitud, Double longitud, String country_code) {
		try {
			log.info("Latitude by phone: "+latitud);
			log.info("Longitude by phone: "+longitud);
			log.info("Country code by phone: "+country_code);
			
			GeoApiContext context = new GeoApiContext.Builder().apiKey(Constant.GOOGLE_GEO_API_KEY).build();
			GeocodingResult[] results = GeocodingApi.reverseGeocode(context, new LatLng(latitud, longitud)).await();
			AddressComponent[] acomponents = results[0].addressComponents;
			String posCountryCode = "";
			
			for (int i = 0; i < acomponents.length; i++) {
				for (int j = 0; j < acomponents[i].types.length; j++) {
					if ((acomponents[i].types[j].toString()).equals("country")) {
						posCountryCode = acomponents[i].shortName;
						break;
			        }
				}
		    }
			
			log.info("Country code by position: "+posCountryCode);
			
			if (!posCountryCode.equals(country_code)) {
				log.info("Position Country ["+posCountryCode+"] <> Phone Country ["+country_code+"].");
				return new Validate(Boolean.FALSE, "El país correspondiente a su SIM card no corresponde al país donde se encuentra actualmente.");
			}
			
			Country country = countryRepository.findOneByCode(posCountryCode);
			
			if (country == null) {
				log.info("Country ["+posCountryCode+"] is null.");
				return new Validate(Boolean.FALSE, "Ha ocurrido un error al obtener el país donde se encuentra.");
			}
			
			if (country.getIsEnabled() == Boolean.FALSE) {
				log.info("Country ["+ country.getName() +"] is disabled.");
				return new Validate(Boolean.FALSE, "Su país de origen ["+country.getName()+"] no se encuentra habilitado para utilizar la aplicación.");
			}
			
			return new Validate(Boolean.TRUE, "");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return new Validate(Boolean.FALSE, "Ha ocurrido un error al validar el país donde se encuentra.");
		}
	}
	
	private Integer insertTracking(Integer user_id, Double latitud, Double longitud, Integer tracking_type) {
		Integer result = null;
		
		try {
			User user = userRepository.findOne(user_id);
			
			if (user == null) {
				log.info("insertTracking(): user is null");
				return result;
			}
			
			GeoTrackerType geoTrackerType = geoTrackerTypeRepository.findOne(tracking_type);
			
			if (geoTrackerType == null) {
				log.info("insertTracking(): geoTrackerType is null");
				return result;
			}
			
			Point position = new GeometryFactory().createPoint(new Coordinate(latitud, longitud));
			
			OfferJob offerJob = offerJobRepository.findOneByOffer(user);
			
			if (offerJob != null) {
				log.info("insertTracking(): updating lastPosition to offerJob: {}", offerJob.getId());
				
				offerJob.setLastPosition(position);
				offerJobRepository.save(offerJob);
			}
			
			GeoTracker geoTracker = new GeoTracker();
			geoTracker.setPosition(position);
			geoTracker.setDate(new Date());
			geoTracker.setUser(user);
			geoTracker.setGeoTrackerType(geoTrackerType);
			geoTrackerRepository.save(geoTracker);
			
			result = geoTracker.getId();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return result;
	}
	
	private Map<String, Object> getUserData(User user, String setupVersion) {
		Map<String, Object> data = new HashMap<String, Object>();
		
		data.put("iduser", user.getId());
		data.put("phone_number", user.getPhoneNumber());
		data.put("email", user.getEmail());
		data.put("name", user.getName());
		data.put("lastname", user.getLastname());
		data.put("photo", (user.getPhoto() == null)?"DEFAULT":user.getPhoto());
		data.put("is_company", user.getIsCompany());
		data.put("is_offer",  user.getIsOffer());
		
		if (user.getIsOffer() == Boolean.TRUE) {
			OfferJob offerJob = offerJobRepository.findOneByOffer(user);
			
			if (offerJob != null) {
				Map<String, Object> offerJobValues = new HashMap<>();
				offerJobValues.put("id", offerJob.getId());
				offerJobValues.put("title", offerJob.getTitle());
				offerJobValues.put("description", offerJob.getDescription());
				offerJobValues.put("service_name", offerJob.getServiceName());
				offerJobValues.put("service_id", offerJob.getService().getId());
				
				List<Proposal> proposals = proposalRepository.findByOfferJob(offerJob);
				List<Integer> offerWorkIds = new ArrayList<Integer>();
				
				if (!proposals.isEmpty()) {
					for (Proposal proposal : proposals) {
						offerWorkIds.add(proposal.getOfferWork().getId());
					}
				}
				offerJobValues.put("offer_works", offerWorkIds);
				
				data.put("offer_job", offerJobValues);
			}
		}
		
		OfferWork offerWork = offerWorkRepository.findLastByClient(user.getId());
		
		if (offerWork != null) {
			Map<String, Object> offerWorkValues = new HashMap<>();
			offerWorkValues.put("id", offerWork.getId());
			offerWorkValues.put("title", offerWork.getTitle());
			offerWorkValues.put("description", offerWork.getDescription());
			offerWorkValues.put("service_name", offerWork.getServiceName());
			offerWorkValues.put("service_id", offerWork.getService().getId());
			offerWorkValues.put("state_name", offerWork.getState().getName());
			offerWorkValues.put("state_id", offerWork.getState().getId());
			
			List<ProposalBasicValue> proposalList = proposalRepository.findByOfferWorkNative(offerWork.getId());
			offerWorkValues.put("proposals", proposalList);
			
			List<ImageWork> imageWorkList = imageWorkRepository.findByOfferWork(offerWork);
			Map<String, Object> imageValues = new HashMap<>();
			int countImage = 0;
			for (Iterator<ImageWork> iterator = imageWorkList.iterator(); iterator.hasNext();) {
				ImageWork imageWork = iterator.next();
				imageValues.put(String.valueOf(countImage++), imageWork.getPath());
			}
			offerWorkValues.put("images", imageValues);
			
			data.put("offer_work", offerWorkValues);
		}
		
		Float countryV = Float.parseFloat(user.getCountry().getSetupVersion());
		Float setupV = Float.parseFloat((setupVersion != null)?setupVersion:"0");
		
		if (countryV.compareTo(setupV) > 0) {
			log.info("Setup country version > Setup user actual version.");
			
			List<Setup> setupList = setupRepository.findByCountry(user.getCountry());
			Map<String, String> setupValues = new HashMap<>();
			setupValues.put("version_setup", user.getCountry().getSetupVersion());
			
			for (Setup setup : setupList) {
				setupValues.put(setup.getKey(), setup.getValue());
			}
			data.put("setup", setupValues);
		}
		
		return data;
	}
}
