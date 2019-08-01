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
    @IBOutlet weak var currentIconImageView: UIImageView!
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
    
    @IBAction func refreshButtonTouched(_ sender: UIBarButtonItem) {
        gpsLocationManager.requestLocation()
        locationManager.fetchDataWithLattLong()
        weatherManager.reloadData(woeid: woeid)
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
        guard let iconImage = weatherManager.weatherAtIndex(0)?.weather_state_abbr else {return}
        let pngImage = "\(iconImage).png"
        let urlString = "https://www.metaweather.com/static/img/weather/png/\(pngImage)"
        currentIconImageView.sd_setImage(with: URL(string: urlString))
        guard let currentWeather = weatherManager.weatherAtIndex(0)?.the_temp else{return}
        currentTemperatureLabel.text = "\(String(format: "%0.2f", currentWeather.toFahrenheit()))°"
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
            //(1°C × 9/5) + 32
            cell.maxTemperatureLabel.text = "Max: \(String(format: "%0.2f", cityWeather.max_temp.toFahrenheit()))°"
            cell.minTemperatureLabel.text = "Min: \(String(format: "%0.2f", cityWeather.min_temp.toFahrenheit()))°"
            cell.humidityLabel.text = "Humidity: \(String(cityWeather.humidity))"
            cell.percentChanceLabel.text = "\(String(cityWeather.predictability))%"
            cell.windLabel.text = "Wind: \(String(format: "%0.2f", cityWeather.wind_speed)) \(cityWeather.wind_direction_compass)"
            
            let pngImage = "\(cityWeather.weather_state_abbr).png"
            let urlString = "https://www.metaweather.com/static/img/weather/png/64/\(pngImage)"
            cell.iconImageView.sd_setImage(with: URL(string: urlString), completed: nil)
            
            
            let dateFormatter = DateFormatter()
            dateFormatter.dateFormat = "yyyy-MM-dd"
            let dateObj = dateFormatter.date(from: cityWeather.applicable_date)
            if let dateObj = dateObj{
                
                
                
                dateFormatter.dateFormat = "E, MMM d"
                cell.dateLabel.text = dateFormatter.string(from: dateObj)
                
            }
            
            
        }
            return cell
        
    }
    
    
}
extension Double {
    func toFahrenheit() -> Double {
        return self * 9 / 5 + 32
    }
    
}


