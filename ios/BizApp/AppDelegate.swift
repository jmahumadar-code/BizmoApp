//
//  AppDelegate.swift
//  BizApp
//
//  Created by Guillermo Fuentes Quijada on 27-09-17.
//  Copyright © 2017 Guillermo Fuentes Quijada. All rights reserved.
//

import UIKit
import GoogleSignIn
import GoogleMaps
import FBSDKLoginKit

@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate, GIDSignInDelegate {
    
    var window: UIWindow?


    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplicationLaunchOptionsKey: Any]?) -> Bool {
        
        GMSServices.provideAPIKey("GoogleMaps_KEY".localized)
        GIDSignIn.sharedInstance().clientID = "GoogleSing".localized
        GIDSignIn.sharedInstance().delegate = self
        GIDSignIn.sharedInstance().signOut()
        FBSDKApplicationDelegate.sharedInstance().application(application, didFinishLaunchingWithOptions: launchOptions)
        
        self.window = UIWindow(frame: UIScreen.main.bounds)
        
        let mainStoryboard: UIStoryboard = UIStoryboard(name: "Main", bundle: nil)
        
        let iduser = UserDefaults.standard.integer(forKey: "id_user")
        
        if iduser == 0 {
            let initialViewController: LoginController = mainStoryboard.instantiateViewController(withIdentifier: "LoginUI") as! LoginController
            self.window?.rootViewController = initialViewController
        }else{
            let initialViewController: MapsController = mainStoryboard.instantiateViewController(withIdentifier: "MapsUI") as! MapsController
            self.window?.rootViewController = initialViewController
        }
        
        return true
    }
    
    func sign(_ signIn: GIDSignIn!, didSignInFor user: GIDGoogleUser!,
              withError error: Error!) {
        if let error = error {
            print("\(error.localizedDescription)")
            NotificationCenter.default.post(
                name: Notification.Name(rawValue: "ToggleAuthUINotification"), object: nil, userInfo: nil)
        } else {
            let userId = user.userID
            let givenName = user.profile.givenName
            let familyName = user.profile.familyName
            let email = user.profile.email
            let profilePhoneUrl = user.profile.imageURL(withDimension: 100).absoluteString
            
            UserDefaults.standard.setValue(givenName, forKey: "firstname_c_user")
            UserDefaults.standard.setValue(familyName, forKey: "lastname_c_user")
            UserDefaults.standard.setValue(userId, forKey: "iduser_c_user")
            UserDefaults.standard.setValue(email, forKey: "email_c_user")
            UserDefaults.standard.setValue("google", forKey: "type_c_user")
            UserDefaults.standard.setValue(true, forKey: "log_c_user")
            UserDefaults.standard.setValue(profilePhoneUrl, forKey: "photoUrl_c_user")
            let mainStoryboard: UIStoryboard = UIStoryboard(name: "Main", bundle: nil)
            let ViewController: RegisterController = mainStoryboard.instantiateViewController(withIdentifier: "RegisterUI") as! RegisterController
            self.window?.rootViewController = ViewController
        }
    }


    func sign(_ signIn: GIDSignIn!, didDisconnectWith user: GIDGoogleUser!,
              withError error: Error!) {
 
    }
    
    func applicationWillResignActive(_ application: UIApplication) {
        FBSDKAppEvents.activateApp()
    }
    
    func application(_ application: UIApplication, open url: URL, sourceApplication: String?, annotation: Any) -> Bool {
        FBSDKApplicationDelegate.sharedInstance().application(application, open: url, sourceApplication: sourceApplication, annotation: annotation)
        GIDSignIn.sharedInstance().handle(url, sourceApplication: sourceApplication, annotation: annotation)
        
        return true
    }

    func applicationDidEnterBackground(_ application: UIApplication) {
        // Use this method to release shared resources, save user data, invalidate timers, and store enough application state information to restore your application to its current state in case it is terminated later.
        // If your application supports background execution, this method is called instead of applicationWillTerminate: when the user quits.
    }

    func applicationWillEnterForeground(_ application: UIApplication) {
        // Called as part of the transition from the background to the active state; here you can undo many of the changes made on entering the background.
    }

    func applicationDidBecomeActive(_ application: UIApplication) {
        // Restart any tasks that were paused (or not yet started) while the application was inactive. If the application was previously in the background, optionally refresh the user interface.
    }

    func applicationWillTerminate(_ application: UIApplication) {
        // Called when the application is about to terminate. Save data if appropriate. See also applicationDidEnterBackground:.
    }


}

