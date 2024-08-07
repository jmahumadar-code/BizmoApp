//
//  ViewController.swift
//  BizApp
//
//  Created by Guillermo Fuentes Quijada on 27-09-17.
//  Copyright © 2017 Guillermo Fuentes Quijada. All rights reserved.
//

import UIKit
import CoreLocation
import FlagKit
import CoreTelephony
import PopupDialog
import FontAwesome_swift
import GoogleSignIn
import FacebookCore
import FBSDKCoreKit
import FacebookLogin
import FBSDKLoginKit

class LoginController: UIViewController, UITextFieldDelegate, CLLocationManagerDelegate, GIDSignInUIDelegate {

    var dict : [String : AnyObject]!
    var locationManager:CLLocationManager!
    
    var openTextPhone = false
    var countryCode:String = ""
    
    var regex:NSRegularExpression? = nil
    var regexPass:NSRegularExpression? = nil
    
    var container: UIView = UIView()
    var loadingView: UIView = UIView()
    var wainting:UIActivityIndicatorView = UIActivityIndicatorView()
    
    //UI References
    @IBOutlet weak var flag: UIImageView!
    @IBOutlet weak var textPhone: UITextField!
    @IBOutlet weak var viewPhone: UIView!
    @IBOutlet weak var heightView: NSLayoutConstraint!
    @IBOutlet weak var textIntro: UILabel!
    @IBOutlet weak var topTextPhone: NSLayoutConstraint!
    @IBOutlet weak var goBack: UIButton!
    @IBOutlet weak var goNext: UIButton!
    @IBOutlet weak var goBackPass: UIButton!
    @IBOutlet weak var viewPass: UIView!
    @IBOutlet weak var widthViewPass: NSLayoutConstraint!
    @IBOutlet weak var posViewPass: NSLayoutConstraint!
    @IBOutlet weak var goNextPass: UIButton!
    @IBOutlet weak var textPass: UITextField!
    @IBOutlet weak var closeRRSS: UIButton!
    @IBOutlet weak var heightRRSS: NSLayoutConstraint!
    @IBOutlet weak var bottomRRSS: NSLayoutConstraint!
    @IBOutlet weak var btn_rrss: UIButton!
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        GIDSignIn.sharedInstance().uiDelegate = self
        
        textPass.setBottomLine(borderColor: UIColor.darkGray)
        textPhone.setBottomLine(borderColor: UIColor.darkGray)
        
        widthViewPass.constant = view.bounds.width
        posViewPass.constant = -1 * view.bounds.width
        heightRRSS.constant = view.bounds.height
        bottomRRSS.constant = -1 * view.bounds.height
        
        goBack.setTitleColor(UIColor.darkGray, for: .normal)
        goBackPass.setTitleColor(UIColor.darkGray, for: .normal)
        closeRRSS.setTitleColor(UIColor.darkGray, for: .normal)
        goNext.setTitleColor(UIColor.lightGray, for: .normal)
        goNextPass.setTitleColor(UIColor.lightGray, for: .normal)
        
        goBackPass.titleLabel?.font = UIFont.fontAwesome(ofSize: 30)
        goBackPass.setTitle(String.fontAwesomeIcon(name: .chevronLeft), for: .normal)
        
        closeRRSS.titleLabel?.font = UIFont.fontAwesome(ofSize: 30)
        closeRRSS.setTitle(String.fontAwesomeIcon(name: .chevronLeft), for: .normal)
        
        goBack.titleLabel?.font = UIFont.fontAwesome(ofSize: 30)
        goBack.setTitle(String.fontAwesomeIcon(name: .chevronLeft), for: .normal)
        
        goNext.titleLabel?.font = UIFont.fontAwesome(ofSize: 80)
        goNext.setTitle(String.fontAwesomeIcon(name: .arrowCircleRight), for: .normal)
        goNext.isEnabled = false
        
        goNextPass.titleLabel?.font = UIFont.fontAwesome(ofSize: 80)
        goNextPass.setTitle(String.fontAwesomeIcon(name: .arrowCircleRight), for: .normal)
        goNextPass.isEnabled = false
        
        topTextPhone.constant = -40
        UIApplication.shared.statusBarStyle = UIStatusBarStyle.lightContent
        UserDefaults.standard.setValue(0, forKey: "id_user")
        
        let net: CTTelephonyNetworkInfo = CTTelephonyNetworkInfo()
        let sim = net.subscriberCellularProvider
        countryCode = (sim?.isoCountryCode?.uppercased() ?? "CL")!
        
        UserDefaults.standard.setValue(countryCode, forKey: "country_code")
        
        var pattern = "rgxEmail".localized
                
        regex = try! NSRegularExpression(pattern: pattern, options: .caseInsensitive)
        pattern = "rgxPass".localized
        
        regexPass = try! NSRegularExpression(pattern: pattern, options: .caseInsensitive)
        
        let flagimg = Flag(countryCode: countryCode)!
        flag.image = flagimg.originalImage
        
         UserDefaults.standard.setValue(countryCode, forKey: "code_phone_user")
        
        viewPhone.layer.shadowColor = UIColor.black.cgColor
        viewPhone.layer.shadowOpacity = 0.4
        viewPhone.layer.shadowOffset = CGSize.zero
        viewPhone.layer.shadowRadius = 8
        
    }
    
    @IBAction func closeRRSS(_ sender: Any) {
        print("btn close: \(btn_rrss.isHidden)")
        bottomRRSS.constant = -1 * view.bounds.height
        UIView.animate(withDuration: 0.3, animations: {
            self.view.layoutIfNeeded()
        })
        UIApplication.shared.statusBarStyle = UIStatusBarStyle.lightContent
    }
    
    @IBAction func openRRSS(_ sender: Any) {
        bottomRRSS.constant = 0
        UIView.animate(withDuration: 0.3, animations: {
            self.view.layoutIfNeeded()
        })
        UIApplication.shared.statusBarStyle = UIStatusBarStyle.default
    }
    
    
    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        
        if openTextPhone {
            textPhone.resignFirstResponder()
        }
        
        if openTextPhone {
            UIApplication.shared.statusBarStyle = UIStatusBarStyle.default
        }else{
            if bottomRRSS.constant == 0 {
                UIApplication.shared.statusBarStyle = UIStatusBarStyle.default
            }else{
                UIApplication.shared.statusBarStyle = UIStatusBarStyle.lightContent
            }
        }
    }
    
    func getFBUserData(){
        if((FBSDKAccessToken.current()) != nil){
            FBSDKGraphRequest(graphPath: "me", parameters: ["fields": "id, first_name, last_name, picture.type(large), email"]).start(completionHandler: { (connection, result, error) -> Void in
                if (error == nil){
                    if let values = result as? NSDictionary {
                        let email = values.object(forKey: "email") as? NSString
                        let firstname = values.object(forKey: "first_name") as? NSString
                        let lastname = values.object(forKey: "last_name") as? NSString
                        let socialId = values.object(forKey: "id") as? NSString
                        let data = values.object(forKey: "picture") as? NSDictionary
                        let profilePhoneURL = (data?.object(forKey: "data") as? NSDictionary)?.object(forKey: "url") as? NSString
                        
                        UserDefaults.standard.setValue(firstname, forKey: "firstname_c_user")
                        UserDefaults.standard.setValue(lastname, forKey: "lastname_c_user")
                        UserDefaults.standard.setValue(socialId, forKey: "iduser_c_user")
                        UserDefaults.standard.setValue(email, forKey: "email_c_user")
                        UserDefaults.standard.setValue(profilePhoneURL, forKey: "photoUrl_c_user")
                        UserDefaults.standard.setValue(true, forKey: "log_c_user")
                        UserDefaults.standard.setValue("google", forKey: "type_c_user")
                        self.initRegister()
                    }
                }
            })
        }
    }
    
    @IBAction func logFacebook(_ sender: Any) {
        let loginManager = LoginManager()
        loginManager.logIn(readPermissions: [.publicProfile, .email], viewController: self) { loginResult in
            switch loginResult {
            case .failed(let error):
                print(error)
            case .cancelled:
                print("User cancelled login.")
            case .success(let grantedPermissions, let declinedPermissions, let accessToken):
                self.getFBUserData()
            }
        }
    }
    
    @IBAction func logGoogleImg(_ sender: Any) {
        GIDSignIn.sharedInstance().signIn()
    }
    
    
    @IBAction func logGoogle(_ sender: Any) {
        GIDSignIn.sharedInstance().signIn()
    }
    
    @IBAction func logFacebookImg(_ sender: Any) {
        let loginManager = LoginManager()
        loginManager.logIn(readPermissions: [.publicProfile, .email], viewController: self) { loginResult in
            switch loginResult {
            case .failed(let error):
                print(error)
            case .cancelled:
                print("User cancelled login.")
            case .success(let grantedPermissions, let declinedPermissions, let accessToken):
                self.getFBUserData()
            }
        }
        
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    
    @IBAction func goMaps(_ sender: Any) {
        self.view.endEditing(true)
        login()
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        super.viewWillDisappear(animated)
        if openTextPhone {
            UIApplication.shared.statusBarStyle = UIStatusBarStyle.default
        }else{
            if bottomRRSS.constant == 0 {
                 UIApplication.shared.statusBarStyle = UIStatusBarStyle.default
            }else{
                 UIApplication.shared.statusBarStyle = UIStatusBarStyle.lightContent
            }
        }
    }
    
    
    @IBAction func phoneChanged(_ sender: Any) {
        let texto = textPhone.text!
        if regex?.numberOfMatches(in: texto, range: NSMakeRange(0, texto.characters.count)) == 1 {
            goNext.isEnabled = true
            goNext.setTitleColor(UIColor.darkGray, for: .normal)
        }else{
            goNext.isEnabled = false
            goNext.setTitleColor(UIColor.lightGray, for: .normal)
        }
    }
    @IBAction func passChanged(_ sender: Any) {
        let texto = textPass.text!
        if regexPass?.numberOfMatches(in: texto, range: NSMakeRange(0, texto.characters.count)) == 1 {
            goNextPass.isEnabled = true
            goNextPass.setTitleColor(UIColor.darkGray, for: .normal)
        }else{
            goNextPass.isEnabled = false
            goNextPass.setTitleColor(UIColor.lightGray, for: .normal)
        }
    }
    
    @IBAction func clickGoBack(_ sender: Any) {
        btn_rrss.isHidden = false
        openTextPhone = false
        self.view.endEditing(true)
        heightView.constant = 220
        topTextPhone.constant = -40
        goBack.isHidden = true
        goNext.isHidden = true
        textIntro.text = "Realiza tu actividad con BizmoBiz"
        UIView.animate(withDuration: 0.3, animations: {
            self.view.layoutIfNeeded()
        })
        UIApplication.shared.statusBarStyle = UIStatusBarStyle.lightContent
        textPhone.placeholder = "Ingrese su email"
        textPhone.text = ""
    }
    
    func startWainting(){
        container.frame = viewPass.frame
        container.center = viewPass.center
        container.backgroundColor = UIColorFromHex(rgbValue: 0xffffff, alpha: 0.3)
        
        loadingView.frame = CGRect(x: 0, y: 0, width: 80, height: 80)
        loadingView.center = viewPass.center
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
        viewPass.addSubview(container)
        
        wainting.startAnimating()
        UIApplication.shared.beginIgnoringInteractionEvents()
    }
    
    @IBAction func clickGoNext(_ sender: Any) {
        posViewPass.constant = 0
        UIView.animate(withDuration: 0.3, animations: {
            self.view.layoutIfNeeded()
        })
        textPass.becomeFirstResponder()
    }
    @IBAction func clickGoBackFromPass(_ sender: Any) {
        posViewPass.constant = -1 * view.bounds.width
        UIView.animate(withDuration: 0.3, animations: {
            self.view.layoutIfNeeded()
        })
        textPass.text = ""
        textPhone.becomeFirstResponder()
    }
    
    func stopWainting(){
        wainting.stopAnimating()
        container.removeFromSuperview()
        UIApplication.shared.endIgnoringInteractionEvents()
    }
    
    @IBAction func clickPhoneField(_ sender: Any) {
        btn_rrss.isHidden = true
        openTextPhone = true
        self.view.endEditing(true)
        heightView.constant = view.bounds.height
        goBack.isHidden = false
        goNext.isHidden = false
        topTextPhone.constant = 20
        textIntro.text = "Ingrese su email"
        UIView.animate(withDuration: 0.3, animations: {
            self.view.layoutIfNeeded()
        })
        UIApplication.shared.statusBarStyle = UIStatusBarStyle.default
        textPhone.placeholder = "example@bizmobiz.com"
        textPhone.resignFirstResponder()
    }
    
    func dialogInit(msg: String){
        let title = "BizmoBiz"
        let message = msg
        let image = UIImage(named: "navimg")
        
        let popup = PopupDialog(title: title, message: message, image: image)
        
        let buttonOne = CancelButton(title: "Cerrar") {
            self.textPass.text = ""
            self.textPass.becomeFirstResponder()
        }
        
        popup.addButtons([buttonOne])
        self.present(popup, animated: true, completion: nil)
    }
    
    func verifyPhone(){
        let group = DispatchGroup()
        var response = false
        var ms = " "
        
        let uri = "http://bizmobiz-dev.sa-east-1.elasticbeanstalk.com/api/mobile/verifyPhone?phone_number=\(textPhone.text ?? "")"
        
        startWainting()
        group.enter()
        
        let url = URL(string: uri)
        if let usableUrl = url {
            let request = URLRequest(url: usableUrl)
            LoginController.execTaskVerify(request: request) { (ok, msg) in
                response = ok
                ms = msg
                group.leave()
            }
        }
        group.notify(queue: DispatchQueue.main) {
            if response {
                UserDefaults.standard.setValue(self.textPhone.text!, forKey: "phone_user")
                self.stopWainting()
                self.initRegister()
            }else{
                self.showError(msg: ms)
            }
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
            let gc = GeneralConstans()
            var response = false
            var ms = " "
            
            var uri = gc.URL_BASE
            uri += gc.REST_LOGIN
            uri += gc.REQUEST_EMAIL
            uri += (textPhone.text?.replacingOccurrences(of: "\\s+$", with: "", options: .regularExpression))!
            uri += gc.REQUEST_AND
            uri += gc.REQUEST_PASSWORD
            uri += (textPass.text?.replacingOccurrences(of: "\\s+$", with: "", options: .regularExpression))!
            uri += gc.REQUEST_AND
            uri += gc.REQUEST_COUNTRY_CODE
            uri += countryCode.replacingOccurrences(of: "\\s+$", with: "", options: .regularExpression)
            uri += gc.REQUEST_AND
            uri += gc.REQUEST_LATITUD
            uri += "\(userLocation.coordinate.latitude)"
            uri += gc.REQUEST_AND
            uri += gc.REQUEST_LONGITUD
            uri += "\(userLocation.coordinate.longitude)"
            uri += gc.REQUEST_AND
            uri += gc.REQUEST_SETUP_VERSION
            uri += "0.5"
            
            print(uri)
            
            startWainting()
            group.enter()
            let url = URL(string: uri)
            if let usableUrl = url {
                let request = URLRequest(url: usableUrl)
                LoginController.execTask(request: request) { (ok, msg) in
                    response = ok
                    ms = msg
                    group.leave()
                }
            }
            group.notify(queue: DispatchQueue.main) {
                if response {
                    UserDefaults.standard.setValue(self.textPhone.text!, forKey: "phone_user")
                    self.stopWainting()
                    self.initMaps()
                }else{
                    self.showError(msg: ms)
                }
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
    
    func login(){
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
    
    private class func execTaskVerify(request: URLRequest, taskCallback: @escaping (Bool,
        String) -> ()) {
        
        let session = URLSession(configuration: URLSessionConfiguration.default)
        let task = session.dataTask(with: request, completionHandler: {(data, response, error) in
            do{
                let parseoDatos = try JSONSerialization.jsonObject(with: data!, options: .allowFragments) as! [String:Any]
                
                let success = parseoDatos["success"] as! Bool
                let msg = parseoDatos["info"] as! String
                taskCallback(success, msg)
            }catch {
                taskCallback(false, "Se ha producido un error en el inicio de sesión")
            }
        })
        
        task.resume()
    }
    
    func showError(msg: String){
        stopWainting()
        dialogInit(msg: msg)
    }
    
    func initMaps(){
        let storyboard: UIStoryboard = UIStoryboard(name: "Main", bundle: nil)
        let mapsController = storyboard.instantiateViewController(withIdentifier: "MapsUI")
        self.present(mapsController, animated: true, completion: nil)
    }
    
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        if !openTextPhone {
            self.view.endEditing(true)
        }
    }
    
    func textFieldShouldReturn(_ textField: UITextField) -> Bool{
        self.view.endEditing(true)
        return (true);
    }
    
    func UIColorFromHex(rgbValue:UInt32, alpha:Double=1.0)->UIColor {
        let red = CGFloat((rgbValue & 0xFF0000) >> 16)/256.0
        let green = CGFloat((rgbValue & 0xFF00) >> 8)/256.0
        let blue = CGFloat(rgbValue & 0xFF)/256.0
        return UIColor(red:red, green:green, blue:blue, alpha:CGFloat(alpha))
    }
    
    func initRegister(){
        let storyboard: UIStoryboard = UIStoryboard(name: "Main", bundle: nil)
        let registerController = storyboard.instantiateViewController(withIdentifier: "RegisterUI")
        self.present(registerController, animated: true, completion: nil)
    }
    @IBAction func createAccount(_ sender: Any) {
        verifyPhone()
    }
    
}

extension String {
    var localized: String {
        return NSLocalizedString(self, tableName: nil, bundle: Bundle.main, value: "", comment: "")
    }
}
extension UITextField {
    
    func setBottomLine(borderColor: UIColor) {
        
        self.borderStyle = UITextBorderStyle.none
        self.backgroundColor = UIColor.clear
        
        let borderLine = UIView()
        let height = 1.0
        borderLine.frame = CGRect(x: 0, y: Double(self.frame.height) - height, width: Double(self.frame.width), height: height)
        
        borderLine.backgroundColor = borderColor
        self.addSubview(borderLine)
    }
    
}
