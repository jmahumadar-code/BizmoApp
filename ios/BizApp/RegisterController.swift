//
//  RegisterController.swift
//  BizApp
//
//  Created by Guillermo Fuentes Quijada on 27-09-17.
//  Copyright © 2017 Guillermo Fuentes Quijada. All rights reserved.
//

import UIKit
import CoreLocation
import FlagKit
import PopupDialog
import Foundation

class RegisterController: UIViewController, UITextFieldDelegate, CLLocationManagerDelegate {
    //UI References
    @IBOutlet weak var titleIniRegister: UILabel!
    @IBOutlet weak var backLogin: UIButton!
    @IBOutlet weak var code1: UITextField!
    @IBOutlet weak var code2: UITextField!
    @IBOutlet weak var code3: UITextField!
    @IBOutlet weak var code4: UITextField!
    @IBOutlet weak var goNext1: UIButton!
    @IBOutlet weak var viewEmail: UIView!
    @IBOutlet weak var widthViewEmail: NSLayoutConstraint!
    @IBOutlet weak var rightMargin: NSLayoutConstraint!
    @IBOutlet weak var goBackLogin: UIButton!
    @IBOutlet weak var goPass: UIButton!
    @IBOutlet weak var textEmail: UITextField!
    @IBOutlet weak var viewPass: UIView!
    @IBOutlet weak var widthViewPass: NSLayoutConstraint!
    @IBOutlet weak var marginRightPass: NSLayoutConstraint!
    @IBOutlet weak var backToEmail: UIButton!
    @IBOutlet weak var goToName: UIButton!
    @IBOutlet weak var textPass: UITextField!
    @IBOutlet weak var viewName: UIView!
    @IBOutlet weak var widthName: NSLayoutConstraint!
    @IBOutlet weak var marginRightName: NSLayoutConstraint!
    @IBOutlet weak var backToPass: UIButton!
    @IBOutlet weak var goToMaps: UIButton!
    @IBOutlet weak var textName: UITextField!
    @IBOutlet weak var textLastName: UITextField!
    @IBOutlet weak var widthCode: NSLayoutConstraint!
    @IBOutlet weak var rightMarginCode: NSLayoutConstraint!
    @IBOutlet weak var marginRRSS: NSLayoutConstraint!
    @IBOutlet weak var widthRRSS: NSLayoutConstraint!
    @IBOutlet weak var flag: UIImageView!
    @IBOutlet weak var codePhoneCountry: UILabel!
    @IBOutlet weak var nameSpecial: UITextField!
    @IBOutlet weak var lastnameSpecial: UITextField!
    @IBOutlet weak var emailSpecial: UITextField!
    @IBOutlet weak var phoneSpecial: UITextField!
    @IBOutlet weak var backSpecialRegister: UIButton!
    @IBOutlet weak var goSpecial: UIButton!
    
    //Attributes
    var container: UIView = UIView()
    var loadingView: UIView = UIView()
    var wainting:UIActivityIndicatorView = UIActivityIndicatorView()
    var locationManager:CLLocationManager!
    
    var regexPhone:NSRegularExpression? = nil
    var regexEmail:NSRegularExpression? = nil
    var regexFirstName:NSRegularExpression? = nil
    var regexLastName:NSRegularExpression? = nil
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        textLastName.setBottomLine(borderColor: UIColor.darkGray)
        textName.setBottomLine(borderColor: UIColor.darkGray)
        textPass.setBottomLine(borderColor: UIColor.darkGray)
        textEmail.setBottomLine(borderColor: UIColor.darkGray)
        
        widthViewPass.constant = view.bounds.width
        widthViewEmail.constant = view.bounds.width
        widthName.constant = view.bounds.width
        widthRRSS.constant = view.bounds.width
        widthCode.constant = view.bounds.width
        rightMargin.constant = -1 * widthViewEmail.constant
        marginRightPass.constant = -1 * widthViewPass.constant
        marginRightName.constant = -1 * widthName.constant
        marginRRSS.constant = -1 * widthRRSS.constant
        rightMarginCode.constant = -1 * widthCode.constant
        
        let countryCode = UserDefaults.standard.string(forKey: "country_code")!
        
        let pattern = "\(countryCode)r".localized
        
        regexPhone = try! NSRegularExpression(pattern: pattern, options: .caseInsensitive)
        regexFirstName = try! NSRegularExpression(pattern: "rgxName".localized, options: .caseInsensitive)
        regexLastName = try! NSRegularExpression(pattern: "rgxLastName".localized, options: .caseInsensitive)
        regexEmail = try! NSRegularExpression(pattern: "rgxEmail".localized, options: .caseInsensitive)
        
        textName.delegate = self
        
        code1.setBottomLine(borderColor: UIColor.darkGray)
        code2.setBottomLine(borderColor: UIColor.darkGray)
        code3.setBottomLine(borderColor: UIColor.darkGray)
        code4.setBottomLine(borderColor: UIColor.darkGray)
        
        nameSpecial.setBottomLine(borderColor: UIColor.darkGray)
        lastnameSpecial.setBottomLine(borderColor: UIColor.darkGray)
        emailSpecial.setBottomLine(borderColor: UIColor.darkGray)
        phoneSpecial.setBottomLine(borderColor: UIColor.darkGray)
        
        goNext1.setTitleColor(UIColor.lightGray, for: .normal)
        
        goNext1.titleLabel?.font = UIFont.fontAwesome(ofSize: 80)
        goNext1.setTitleColor(UIColor.lightGray, for: .normal)
        goNext1.setTitle(String.fontAwesomeIcon(name: .arrowCircleRight), for: .normal)
        goNext1.isEnabled = false
        
        goToMaps.titleLabel?.font = UIFont.fontAwesome(ofSize: 80)
        goToMaps.setTitleColor(UIColor.lightGray, for: .normal)
        goToMaps.setTitle(String.fontAwesomeIcon(name: .arrowCircleRight), for: .normal)
        goToMaps.isEnabled = false
        
        goSpecial.titleLabel?.font = UIFont.fontAwesome(ofSize: 80)
        goSpecial.setTitleColor(UIColor.lightGray, for: .normal)
        goSpecial.setTitle(String.fontAwesomeIcon(name: .arrowCircleRight), for: .normal)
        goSpecial.isEnabled = false
        
        goPass.titleLabel?.font = UIFont.fontAwesome(ofSize: 80)
        goPass.setTitleColor(UIColor.lightGray, for: .normal)
        goPass.setTitle(String.fontAwesomeIcon(name: .arrowCircleRight), for: .normal)
        goPass.isEnabled = false
        
        goToName.titleLabel?.font = UIFont.fontAwesome(ofSize: 80)
        goToName.setTitleColor(UIColor.lightGray , for: .normal)
        goToName.setTitle(String.fontAwesomeIcon(name: .arrowCircleRight), for: .normal)
        goToName.isEnabled = false
        
        backToEmail.setTitleColor(UIColor.darkGray, for: .normal)
        backToEmail.titleLabel?.font = UIFont.fontAwesome(ofSize: 30)
        backToEmail.setTitle(String.fontAwesomeIcon(name: .chevronLeft), for: .normal)
        
        backSpecialRegister.setTitleColor(UIColor.darkGray, for: .normal)
        backSpecialRegister.titleLabel?.font = UIFont.fontAwesome(ofSize: 30)
        backSpecialRegister.setTitle(String.fontAwesomeIcon(name: .chevronLeft), for: .normal)
        
        UIApplication.shared.statusBarStyle = UIStatusBarStyle.default
        backLogin.setTitleColor(UIColor.darkGray, for: .normal)
        backLogin.titleLabel?.font = UIFont.fontAwesome(ofSize: 30)
        backLogin.setTitle(String.fontAwesomeIcon(name: .chevronLeft), for: .normal)
        
        backToPass.setTitleColor(UIColor.darkGray, for: .normal)
        backToPass.titleLabel?.font = UIFont.fontAwesome(ofSize: 30)
        backToPass.setTitle(String.fontAwesomeIcon(name: .chevronLeft), for: .normal)
        
        goBackLogin.setTitleColor(UIColor.darkGray, for: .normal)
        goBackLogin.titleLabel?.font = UIFont.fontAwesome(ofSize: 30)
        goBackLogin.setTitle(String.fontAwesomeIcon(name: .chevronLeft), for: .normal)
        
        //let phone = UserDefaults.standard.string(forKey: "phone_number")
        
        
        let special = UserDefaults.standard.bool(forKey: "log_c_user")
        
        if special {
            let flagimg = Flag(countryCode: countryCode)!
            flag.image = flagimg.originalImage
            codePhoneCountry.text = "+\(countryCode.localized)"
            nameSpecial.text = UserDefaults.standard.string(forKey: "firstname_c_user")
            lastnameSpecial.text = UserDefaults.standard.string(forKey: "lastname_c_user")
            emailSpecial.text = UserDefaults.standard.string(forKey: "email_c_user")
            marginRRSS.constant = 0
            phoneSpecial.becomeFirstResponder()
        }else {
            textEmail.becomeFirstResponder()
            rightMargin.constant = 0
        }
    }
    
    @IBAction func changeEmail(_ sender: Any) {
        let texto = textEmail.text!
        if regexEmail?.numberOfMatches(in: texto, range: NSMakeRange(0, texto.characters.count)) == 1 {
            goPass.isEnabled = true
            goPass.setTitleColor(UIColor.darkGray, for: .normal)
        }else {
            goPass.isEnabled = false
            goPass.setTitleColor(UIColor.lightGray, for: .normal)
        }
    }
    
    @IBAction func goToCodeSpecial(_ sender: Any) {
        titleIniRegister.text = "Ingresa el código que te enviamos al email \(emailSpecial.text ?? "")"
        rightMarginCode.constant = 0
        UIView.animate(withDuration: 0.3, animations: {
            self.view.layoutIfNeeded()
        })
        code1.becomeFirstResponder()
    }
    
    
    @IBAction func goBackSpecial(_ sender: Any) {
        UserDefaults.standard.setValue(false, forKey: "log_c_user")
        initLogin()
    }
    
    
    @IBAction func changePhoneSpecial(_ sender: Any) {
        let name = nameSpecial.text!
        let lastname = lastnameSpecial.text!
        let email = emailSpecial.text!
        let phone = phoneSpecial.text!
        if regexFirstName?.numberOfMatches(in: name, range: NSMakeRange(0, name.characters.count)) == 1 && regexLastName?.numberOfMatches(in: lastname, range: NSMakeRange(0, lastname.characters.count)) == 1 && regexEmail?.numberOfMatches(in: email, range: NSMakeRange(0, email.characters.count)) == 1 && regexPhone?.numberOfMatches(in: phone, range: NSMakeRange(0, phone.characters.count)) == 1 {
            goSpecial.isEnabled = true
            goSpecial.setTitleColor(UIColor.darkGray, for: .normal)
        }else{
            goSpecial.isEnabled = false
            goSpecial.setTitleColor(UIColor.lightGray, for: .normal)
        }
    }
    
    
    @IBAction func clickGoToEmail(_ sender: Any) {
        code1.text = ""
        code2.text = ""
        code3.text = ""
        code4.text = ""
        rightMarginCode.constant = -1 * widthCode.constant
        marginRightPass.constant = -1 * widthViewPass.constant
        UIView.animate(withDuration: 0.3, animations: {
            self.view.layoutIfNeeded()
        })
        textEmail.becomeFirstResponder()
    }
    
    @IBAction func clickGoToName(_ sender: Any) {
        let special = UserDefaults.standard.bool(forKey: "log_c_user")
        
        if special {
            registerUser()
        }else{
            marginRightName.constant = 0
            UIView.animate(withDuration: 0.3, animations: {
                self.view.layoutIfNeeded()
            })
            textName.becomeFirstResponder()
        }
    }
    @IBAction func clickGoToMap(_ sender: Any) {
        registerUser()
    }
    
    @IBAction func changeTextPass(_ sender: Any) {
        if (textPass.text?.characters.count ?? 0) > 5 {
            goToName.isEnabled = true
            goToName.setTitleColor(UIColor.darkGray, for: .normal)
        } else {
            goToName.isEnabled = true
            goToName.setTitleColor(UIColor.lightGray, for: .normal)
        }
    }
    
    @IBAction func changeLastName(_ sender: Any) {
        let texto = textName.text!
        let texto1 = textLastName.text!
        if regexFirstName?.numberOfMatches(in: texto, range: NSMakeRange(0, texto.characters.count)) == 1 && regexLastName?.numberOfMatches(in: texto1, range: NSMakeRange(0, texto1.characters.count)) == 1 {
            goToMaps.isEnabled = true
            goToMaps.setTitleColor(UIColor.darkGray, for: .normal)
        }else{
            goToMaps.isEnabled = false
            goToMaps.setTitleColor(UIColor.lightGray, for: .normal)
        }
    }
    
    @IBAction func clickBackToPass(_ sender: Any) {
        marginRightName.constant = -1 * widthName.constant
        UIView.animate(withDuration: 0.3, animations: {
            self.view.layoutIfNeeded()
        })
        textPass.becomeFirstResponder()
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    
    @IBAction func editCode(_ sender: Any) {
        if code1.text?.characters.count == 1 {
            code2.becomeFirstResponder()
        }
    }
    
    @IBAction func editCode2(_ sender: Any) {
        if code2.text?.characters.count == 1 {
            code3.becomeFirstResponder()
        }
        
        if code2.text?.characters.count == 0 {
            code1.becomeFirstResponder()
        }
    }
    
    @IBAction func editCode3(_ sender: Any) {
        if code3.text?.characters.count == 1 {
            code4.becomeFirstResponder()
        }
        
        if code3.text?.characters.count == 0 {
            code2.becomeFirstResponder()
        }
    }
    
    @IBAction func editCode4(_ sender: Any) {
        if code4.text?.characters.count == 1 {
            goNext1.isEnabled = true
            let cod = "\(code1.text ?? "")\(code2.text ?? "")\(code3.text ?? "")\(code4.text ?? "")"
            
            if cod == "1234" {
                marginRightPass.constant = 0
                UIView.animate(withDuration: 0.3, animations: {
                    self.view.layoutIfNeeded()
                })
                textPass.becomeFirstResponder()
            } else{
                dialogCodeError()
            }
            
        }
        if code4.text?.characters.count == 0 {
            code3.becomeFirstResponder()
        }
    }
    
    func dialogCodeError(){
        let title = "BizmoBiz"
        let message = "El código ingresado es incorrecto"
        let image = UIImage(named: "navimg")
        
        let popup = PopupDialog(title: title, message: message, image: image)
        
        let buttonOne = CancelButton(title: "Cerrar") {
           
        }
        
        popup.addButtons([buttonOne])
        self.present(popup, animated: true, completion: nil)
    }
    
    @IBAction func goBackToLogin(_ sender: Any) {
        initLogin()
    }
    
    @IBAction func goToPass(_ sender: Any) {
        titleIniRegister.text = "Ingresa el código que te enviamos al email \(textEmail.text ?? "")"
        rightMarginCode.constant = 0
        UIView.animate(withDuration: 0.3, animations: {
            self.view.layoutIfNeeded()
        })
        code1.becomeFirstResponder()
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        super.viewWillDisappear(animated)
        UIApplication.shared.statusBarStyle = UIStatusBarStyle.default
 
    }

    func registerUser(){
        locationManager = CLLocationManager()
        locationManager.delegate = self
        locationManager.desiredAccuracy = kCLLocationAccuracyBest
        locationManager.requestAlwaysAuthorization()
        
        if CLLocationManager.locationServicesEnabled() {
            locationManager.startUpdatingLocation()
        }else{
            dialogErrorGPS()
        }
    }
    
    func locationManager(_ manager: CLLocationManager, didChangeAuthorization status: CLAuthorizationStatus) {
        switch status {
        case .restricted:
            print("Location access was restricted.")
        case .denied:
            print("User denied access to location.")
            dialogErrorGPS()
        case .notDetermined:
            print("Location status not determined.")
        case .authorizedAlways: fallthrough
        case .authorizedWhenInUse:
            print("Location status is OK.")
            locationManager.startUpdatingLocation()
        }
    }
    
    func locationManager(_ manager: CLLocationManager, didUpdateLocations locations: [CLLocation]) {
        let userLocation:CLLocation = locations[0] as CLLocation
        if userLocation.coordinate.latitude != 0 {
            let group = DispatchGroup()
            
            var response = false
            var ms:String = ""
            
            let special = UserDefaults.standard.bool(forKey: "log_c_user")
            
            let code:String = UserDefaults.standard.string(forKey: "country_code") ?? "CL"
            let latitud:String = "\(userLocation.coordinate.latitude)"
            let longitud:String = "\(userLocation.coordinate.longitude)"
            var name:String = ""
            var lastname:String = ""
            var email:String = ""
            var phone:String = ""
            
            if special {
                name = nameSpecial.text ?? "user"
                lastname = lastnameSpecial.text ?? "lastname"
                email = emailSpecial.text ?? "mail"
                phone = phoneSpecial.text ?? "123456789"
            }else{
                name = textName.text ?? "user"
                lastname = textLastName.text ?? "lastname"
                email = textEmail.text ?? "mail"
                phone = UserDefaults.standard.string(forKey: "phone_user") ?? "123456789"
            }
            
            name = name.replacingOccurrences(of: "\\s+$", with: "", options: .regularExpression)
            lastname = lastname.replacingOccurrences(of: "\\s+$", with: "", options: .regularExpression)
            email = email.replacingOccurrences(of: "\\s+$", with: "", options: .regularExpression)
            phone = phone.replacingOccurrences(of: "\\s+$", with: "", options: .regularExpression)
            
            let verificar:Bool = regexFirstName?.numberOfMatches(in: name, range: NSMakeRange(0, name.characters.count)) == 1 && regexLastName?.numberOfMatches(in: lastname, range: NSMakeRange(0, lastname.characters.count)) == 1 && regexPhone?.numberOfMatches(in: phone, range: NSMakeRange(0, phone.characters.count)) == 1 && regexEmail?.numberOfMatches(in: email, range: NSMakeRange(0, email.characters.count)) == 1
            
            if verificar {
                let uri = "http://bizmobiz-dev.sa-east-1.elasticbeanstalk.com/api/mobile/register?name=\(name)&lastname=\(lastname)&country_code=\(code)&email=\(email)&password=\(textPass.text ?? "12345")&phone_number=\(phone)&latitud=\(latitud)&longitud=\(longitud)"
                
                print(uri)
                startWainting()
                group.enter()
                let url = URL(string: uri)
                if let usableUrl = url {
                    let request = URLRequest(url: usableUrl)
                    RegisterController.execTask(request: request) { (ok, msg) in
                        response = ok
                        ms = msg
                        group.leave()
                    }
                }
                group.notify(queue: DispatchQueue.main) {
                    manager.stopUpdatingLocation()
                    if response {
                        self.stopWainting()
                        self.initMaps()
                    }else{
                        self.stopWainting()
                        self.dialogError(msg: ms)
                    }
                }
            }else{
                let title = "BizmoBiz"
                let message = "Se ha producido un error al crear su cuenta en BizmoBiz."
                let image = UIImage(named: "navimg")
                
                let popup = PopupDialog(title: title, message: message, image: image)
                
                let buttonOne = CancelButton(title: "Cerrar") {
                    
                }
                
                popup.addButtons([buttonOne])
                self.present(popup, animated: true, completion: nil)
            }
        }else{
            let title = "BizmoBiz"
            let message = "error_gps".localized
            let image = UIImage(named: "navimg")
            
            let popup = PopupDialog(title: title, message: message, image: image)
            
            let buttonTwo = DefaultButton(title: "Configuración") {
                if let url = URL(string: "App-Prefs:root=Privacy&path=LOCATION/com.bizmolimited.bizapp"){
                    UIApplication.shared.open(url, options: [:], completionHandler: nil)
                }
            }
            
            popup.addButtons([buttonTwo])
            self.present(popup, animated: true, completion: nil)
        }
    }
    
    func dialogErrorGPS(){
        let title = "BizmoBiz"
        let message = "error_gps".localized
        let image = UIImage(named: "navimg")
        
        let popup = PopupDialog(title: title, message: message, image: image)
        
        let buttonTwo = DefaultButton(title: "Configuración") {
            if let url = URL(string: "App-Prefs:root=Privacy&path=LOCATION/com.bizmolimited.bizapp"){
                UIApplication.shared.open(url, options: [:], completionHandler: nil)
            }
        }
        
        popup.addButtons([buttonTwo])
        self.present(popup, animated: true, completion: nil)
    }
    
    func dialogError(msg: String){
        let title = "BizmoBiz"
        let message = msg
        let image = UIImage(named: "navimg")
        
        let popup = PopupDialog(title: title, message: message, image: image)
        
        let buttonOne = CancelButton(title: "Cerrar") {
            
        }
        
        popup.addButtons([buttonOne])
        self.present(popup, animated: true, completion: nil)
    }
    
    private class func execTask(request: URLRequest, taskCallback: @escaping (Bool,
        String) -> ()) {
        
        let session = URLSession(configuration: URLSessionConfiguration.default)
        let task = session.dataTask(with: request, completionHandler: {(data, response, error) in
            do{
                let parseoDatos = try JSONSerialization.jsonObject(with: data!, options: .allowFragments) as! [String:Any]
                
                let success = parseoDatos["success"] as! Bool
                let msg = parseoDatos["info"] as! String
                if success {
                    let datos = parseoDatos["data"] as! [String:Any]
                    let iduser = datos["iduser"] as! Int
                    let name = datos["name"] as! String
                    let lastname = datos["lastname"] as! String
                    let email = datos["email"] as! String
                    let photo = datos["photo"] as! String
                    let is_company = datos["is_company"] as! Bool
                    let is_offerer = datos["is_offerer"] as! Bool
                    
                    UserDefaults.standard.setValue(iduser, forKey: "id_user")
                    UserDefaults.standard.setValue(name, forKey: "name_user")
                    UserDefaults.standard.setValue(lastname, forKey: "lastname_user")
                    UserDefaults.standard.setValue(photo, forKey: "photo_user")
                    UserDefaults.standard.setValue(email, forKey: "email_user")
                    UserDefaults.standard.setValue(is_company, forKey: "is_company_user")
                    UserDefaults.standard.setValue(is_offerer, forKey: "is_offerer_user")
                }
                taskCallback(success, msg)
            }catch {
                taskCallback(false, "Se ha producido un error en el inicio de sesión")
            }
        })
        
        task.resume()
    }
 
    func locationManager(_ manager: CLLocationManager, didFailWithError error: Error){
        print("Error \(error)")
    }
    
    func initMaps(){
        let storyboard: UIStoryboard = UIStoryboard(name: "Main", bundle: nil)
        let mapsController = storyboard.instantiateViewController(withIdentifier: "MapsUI")
        self.present(mapsController, animated: true, completion: nil)
    }
    
    func initLogin(){
        let storyboard: UIStoryboard = UIStoryboard(name: "Main", bundle: nil)
        let loginController = storyboard.instantiateViewController(withIdentifier: "LoginUI")
        self.present(loginController, animated: true, completion: nil)
    }

    func startWainting(){
        container.frame = self.view.frame
        container.center = self.view.center
        container.backgroundColor = UIColorFromHex(rgbValue: 0xffffff, alpha: 0.3)
        
        loadingView.frame = CGRect(x: 0, y: 0, width: 80, height: 80)
        loadingView.center = self.view.center
        loadingView.backgroundColor = UIColorFromHex(rgbValue: 0x444444, alpha: 0.7)
        loadingView.clipsToBounds = true
        loadingView.layer.cornerRadius = 10
        
        
        wainting.hidesWhenStopped = true
        wainting.frame = CGRect(x: 0, y: 0, width: 40, height: 40)
        wainting.activityIndicatorViewStyle = UIActivityIndicatorViewStyle.whiteLarge
        wainting.center = CGPoint(x: loadingView.frame.size.width / 2,
                                  y: loadingView.frame.size.height / 2)
        
        loadingView.addSubview(wainting)
        container.addSubview(loadingView)
        view.addSubview(container)
        
        wainting.startAnimating()
        UIApplication.shared.beginIgnoringInteractionEvents()
    }
    
    func stopWainting(){
        wainting.stopAnimating()
        container.removeFromSuperview()
        UIApplication.shared.endIgnoringInteractionEvents()
    }
    
    @IBAction func goBackLogin(_ sender: Any) {
        code1.text = ""
        code2.text = ""
        code3.text = ""
        code4.text = ""
        rightMarginCode.constant = -1 * widthCode.constant
        UIView.animate(withDuration: 0.3, animations: {
            self.view.layoutIfNeeded()
        })
        textEmail.becomeFirstResponder()
    }
    
    func UIColorFromHex(rgbValue:UInt32, alpha:Double=1.0)->UIColor {
        let red = CGFloat((rgbValue & 0xFF0000) >> 16)/256.0
        let green = CGFloat((rgbValue & 0xFF00) >> 8)/256.0
        let blue = CGFloat(rgbValue & 0xFF)/256.0
        return UIColor(red:red, green:green, blue:blue, alpha:CGFloat(alpha))
    }
    
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        self.view.endEditing(true)
    }
    
    func textFieldShouldReturn(_ textField: UITextField) -> Bool{
        if textField == textName {
            textLastName.becomeFirstResponder()
        }else{
            self.view.endEditing(true)
        }
        return (true);
    }

    
}
