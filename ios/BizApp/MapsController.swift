//
//  MapsController.swift
//  BizApp
//
//  Created by Guillermo Fuentes Quijada on 27-09-17.
//  Copyright © 2017 Guillermo Fuentes Quijada. All rights reserved.
//

import UIKit
import PopupDialog
import GoogleMaps

class MapsController: UIViewController, UITextFieldDelegate, GMSMapViewDelegate {
    //UI References
    @IBOutlet weak var mapsView: GMSMapView!
    @IBOutlet weak var viewContainer: UIView!
    @IBOutlet weak var menu_btn: UIButton!
    @IBOutlet weak var scheduled_btn: UIButton!
    @IBOutlet weak var search: UITextField!
    @IBOutlet weak var menuView: UIView!
    @IBOutlet weak var labelNameUser: UILabel!
    @IBOutlet weak var start: UILabel!
    @IBOutlet weak var image: UIImageView!
    //Constraint References
    @IBOutlet weak var posNavView: NSLayoutConstraint!
    @IBOutlet weak var widthMenu: NSLayoutConstraint!
    
    //Atrubutes
    let ID = "iduser=\(UserDefaults.standard.integer(forKey: "id_user"))"

    var menuOpen = false;
    var locationManager = CLLocationManager()
    var currentLocation: CLLocation?
    var zoomLevel: Float = 16.0
    let defaultLocation = CLLocation(latitude: -33.869405, longitude: 151.199)

    override func viewDidLoad() {
        super.viewDidLoad()
        
        image.layer.cornerRadius = image.frame.size.width/2
        image.clipsToBounds = true
        
        start.textColor = UIColor.yellow
        
        start?.font = UIFont.fontAwesome(ofSize: 20)
        start.text = String.fontAwesomeIcon(name: .star)
        
        menu_btn.setTitleColor(UIColor.darkGray, for: .normal)
        scheduled_btn.setTitleColor(UIColor.lightGray, for: .normal)
        
        menu_btn.titleLabel?.font = UIFont.fontAwesome(ofSize: 30)
        menu_btn.setTitle(String.fontAwesomeIcon(name: .bars), for: .normal)
        
        scheduled_btn.titleLabel?.font = UIFont.fontAwesome(ofSize: 30)
        scheduled_btn.setTitle(String.fontAwesomeIcon(name: .clockO), for: .normal)
        
        let fullname = "\(UserDefaults.standard.string(forKey: "name_user") ?? "user") \(UserDefaults.standard.string(forKey: "lastname_user") ?? "")"
        
        self.labelNameUser.text = fullname 
        UIApplication.shared.statusBarStyle = UIStatusBarStyle.default
        widthMenu.constant = (view.bounds.width/4)*3
        
        self.posNavView.constant = -1 * widthMenu.constant
        
        self.locationManager = CLLocationManager()
        self.locationManager.desiredAccuracy = kCLLocationAccuracyBest
        self.locationManager.delegate = self
        self.locationManager.requestAlwaysAuthorization()
        self.locationManager.startUpdatingLocation()
        
            
        let camera = GMSCameraPosition.camera(withLatitude: self.defaultLocation.coordinate.latitude,
                                              longitude: self.defaultLocation.coordinate.longitude,
                                              zoom: self.zoomLevel)
        self.mapsView.camera = camera
        self.mapsView.delegate = self
        self.mapsView?.isMyLocationEnabled = true
        self.mapsView?.settings.myLocationButton = true
        self.mapsView.autoresizingMask = [.flexibleWidth, .flexibleHeight]
        self.mapsView.isHidden = true
        
        do {
            // Set the map style by passing the URL of the local file.
            if let styleURL = Bundle.main.url(forResource: "style_map", withExtension: "json") {
                self.mapsView.mapStyle = try GMSMapStyle(contentsOfFileURL: styleURL)
            } else {
                NSLog("Unable to find style.json")
            }
        } catch {
            NSLog("One or more of the map styles failed to load. \(error)")
        }
        
        self.viewContainer.layer.shadowColor = UIColor.black.cgColor
        self.viewContainer.layer.shadowOpacity = 0.4
        self.viewContainer.layer.shadowOffset = CGSize.zero
        self.viewContainer.layer.shadowRadius = 8
        
        self.menuView.layer.shadowColor = UIColor.black.cgColor
        self.menuView.layer.shadowOpacity = 0.4
        self.menuView.layer.shadowOffset = CGSize.zero
        self.menuView.layer.shadowRadius = 8
        
        self.search.placeholder = "search".localized
        
    }
    
    @IBAction func clickExit(_ sender: Any) {
        let title = "BizmoBiz"
        let message = "¿Desea cerrar su sesión en BizmoBiz?"
        let image = UIImage(named: "navimg")
        
        let popup = PopupDialog(title: title, message: message, image: image)
        
        let buttonOne = CancelButton(title: "Cerrar"){
            print("close")
        }
        
        let buttonTwo = DefaultButton(title: "Salir") {
            UserDefaults.standard.setValue(0, forKey: "id_user")
            self.locationManager.stopUpdatingLocation()
            let storyboard: UIStoryboard = UIStoryboard(name: "Main", bundle: nil)
            let mapsController = storyboard.instantiateViewController(withIdentifier: "LoginUI")
            self.present(mapsController, animated: true, completion: nil)
        }
        
        popup.addButtons([buttonOne, buttonTwo])
        self.present(popup, animated: true, completion: nil)
    }
    
    @IBAction func menuClick(_ sender: Any) {
        if (menuOpen) {
            posNavView.constant = -1 * widthMenu.constant
            UIView.animate(withDuration: 0.3, animations: {
                self.view.layoutIfNeeded()
            })
        } else {
            posNavView.constant = 0
            UIView.animate(withDuration: 0.3, animations: {
                self.view.layoutIfNeeded()
            })
        }
        menuOpen = !menuOpen
    }
    
    func mapView(_ mapView: GMSMapView, idleAt position: GMSCameraPosition) {
        mapsView.isMyLocationEnabled = true
    }
    
    func mapView(_ mapView: GMSMapView, willMove gesture: Bool) {
        mapsView.isMyLocationEnabled = true
        
        if (gesture) {
            mapView.selectedMarker = nil
        }
    }
    
    func mapView(_ mapView: GMSMapView, didTap marker: GMSMarker) -> Bool {
        mapsView.isMyLocationEnabled = true
        return false
    }
    
    func mapView(_ mapView: GMSMapView, didTapAt coordinate: CLLocationCoordinate2D) {
        if (menuOpen) {
            menuOpen = !menuOpen
            posNavView.constant =  -1 * widthMenu.constant
            UIView.animate(withDuration: 0.3, animations: {
                self.view.layoutIfNeeded()
            })
        }
    }
    
    func didTapMyLocationButton(for mapView: GMSMapView) -> Bool {
        mapsView.isMyLocationEnabled = true
        mapsView.selectedMarker = nil
        return false
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        super.viewWillDisappear(animated)
        UIApplication.shared.statusBarStyle = UIStatusBarStyle.default
    }
    
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        self.view.endEditing(true)
    }
    
    func textFieldShouldReturn(_ textField: UITextField) -> Bool{
        return (true);
    }
    
    func dialogError(msg: String){
        let title = "BizmoBiz"
        let message = msg
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
    
    func refreshMap(){
        let camera = GMSCameraPosition.camera(withLatitude: (locationManager.location?.coordinate.latitude)!,
                                              longitude: (locationManager.location?.coordinate.longitude)!,
                                              zoom: zoomLevel)
        if mapsView.isHidden {
            mapsView.isHidden = false
            mapsView.camera = camera
        } else {
            mapsView.animate(to: camera)
        }
    }
    
}

extension MapsController: CLLocationManagerDelegate {
    
    func locationManager(_ manager: CLLocationManager, didUpdateLocations locations: [CLLocation]) {
        let location: CLLocation = locations.last!
        
        let camera = GMSCameraPosition.camera(withLatitude: location.coordinate.latitude,
                                              longitude: location.coordinate.longitude,
                                              zoom: zoomLevel)
        let gc = GeneralConstans()
        
        var uri = gc.URL_BASE
        uri += gc.REST_TRACKING
        uri += gc.REQUEST_ID_USER
        uri += ID
        uri += gc.REQUEST_AND
        uri += gc.REQUEST_LATITUD
        uri += "\(location.coordinate.latitude)"
        uri += gc.REQUEST_AND
        uri += gc.REQUEST_LONGITUD
        uri += "\(location.coordinate.longitude)"

        
        let url = URL(string: uri)
                
        if let usableUrl = url {
            let request = URLRequest(url: usableUrl)
            let task = URLSession.shared.dataTask(with: request, completionHandler: { (data, response, error) in
            })
            task.resume()
        }
        
        if mapsView.isHidden {
            mapsView.isHidden = false
            mapsView.camera = camera
        } else {
            mapsView.animate(to: camera)
        }
        
    }
    
    // Handle authorization for the location manager.
    func locationManager(_ manager: CLLocationManager, didChangeAuthorization status: CLAuthorizationStatus) {
        switch status {
        case .restricted:
            print("Location access was restricted.")
        case .denied:
            print("User denied access to location.")
            mapsView.isHidden = true
            dialogError(msg: "error_gps".localized)
        case .notDetermined:
            print("Location status not determined.")
        case .authorizedAlways: fallthrough
        case .authorizedWhenInUse:
            print("Location status is OK.")
            refreshMap()
        }
    }
    
    // Handle location manager errors.
    func locationManager(_ manager: CLLocationManager, didFailWithError error: Error) {
        locationManager.stopUpdatingLocation()
        print("Error: \(error)")
    }
}
