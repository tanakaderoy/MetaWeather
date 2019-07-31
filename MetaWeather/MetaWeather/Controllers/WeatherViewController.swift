//
//  WeatherViewController.swift
//  MetaWeather
//
//  Created by Tanaka Mazivanhanga on 7/31/19.
//  Copyright © 2019 Tanaka Mazivanhanga. All rights reserved.
//

import UIKit
import CoreLocation
import SDWebImage


class WeatherViewController: UIViewController, CLLocationManagerDelegate {
    @IBOutlet weak var currentCityLabel: UILabel!
    @IBOutlet weak var currentTemperatureLabel: UILabel!
    @IBOutlet weak var tableView: UITableView!
    var gotLocation = false
    let locationManager = LocationManager()
    let weatherManager = WeatherManager()
    
    let gpsLocationManager = CLLocationManager()
    
    var latitude = ""
    var longitude = ""
    var woeid = 0
  
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        weatherManager.delegate = self
        
        
        locationManager.delegate = self
        
        
        
        
        
        gpsLocationManager.delegate = self
        gpsLocationManager.desiredAccuracy = kCLLocationAccuracyHundredMeters
        gpsLocationManager.requestWhenInUseAuthorization()
        gpsLocationManager.startUpdatingLocation()
        
        locationManager.reloadData()

        weatherManager.reloadData(woeid: woeid)
        
        
        // Do any additional setup after loading the view.
    }
    func initilizeWeather(){
        if gotLocation {
            locationManager.latt = latitude
            locationManager.long = longitude
            locationManager.fetchDataWithLattLong()
            
            
        }else{
            locationManager.reloadData()
            
        }
        
        
    }
    
    
    func locationManager(_ manager: CLLocationManager, didUpdateLocations locations: [CLLocation]) {
        let location = locations[locations.count - 1]
        if location.horizontalAccuracy > 0{
            gotLocation = true
            gpsLocationManager.stopUpdatingLocation()
            gpsLocationManager.delegate = nil
            print("longitude \(location.coordinate.longitude) latitude \(location.coordinate.latitude)")
            latitude = "\(location.coordinate.latitude)"
            longitude = "\(location.coordinate.longitude)"
            initilizeWeather()
            
        }
    }
    func locationManager(_ manager: CLLocationManager, didFailWithError error: Error) {
        print(error)
        currentCityLabel.text = "Location Unavailable"
    }
    

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destination.
        // Pass the selected object to the new view controller.
    }
    */

}
extension WeatherViewController: LocationManagerDelegate {
    func locationUpdated() {
        
        currentCityLabel.text = locationManager.location[0].title
        self.woeid = locationManager.location[0].woeid
        
        weatherManager.reloadData(woeid: woeid)
        weatherUpdated()
        
        
        
        
        
       
    }
    
    
}


extension WeatherViewController: WeatherManagerDelegate {
    func weatherUpdated() {
        
        guard let currentWeather = weatherManager.weatherAtIndex(0)?.the_temp else{return}
        currentTemperatureLabel.text = "\(String(format: "%0.2f", currentWeather))°"
       self.tableView.reloadData()
        
    }
    
    
}

extension WeatherViewController: UITableViewDelegate, UITableViewDataSource {
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return weatherManager.weather?.count ?? 0
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "weatherCell", for: indexPath) as! WeatherTableViewCell
        
        if let cityWeather = weatherManager.weatherAtIndex(indexPath.row) {
            cell.maxTemperatureLabel.text = "Max: \(String(format: "%0.2f", cityWeather.max_temp))°"
            cell.minTemperatureLabel.text = "Min: \(String(format: "%0.2f", cityWeather.min_temp))°"
            cell.humidityLabel.text = "Humidity: \(String(cityWeather.humidity))"
            cell.percentChanceLabel.text = "\(String(cityWeather.predictability))%"
            cell.windLabel.text = "Wind: \(String(format: "%0.2f", cityWeather.wind_speed)) \(cityWeather.wind_direction_compass)"
            
            let pngImage = "\(cityWeather.weather_state_abbr).png"
            let urlString = "https://www.metaweather.com/static/img/weather/png/64/\(pngImage)"
            cell.iconImageView.sd_setImage(with: URL(string: urlString), completed: nil)
            
        }
            return cell
        
    }
    
    
}


