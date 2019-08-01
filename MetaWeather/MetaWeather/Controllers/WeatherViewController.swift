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
    @IBOutlet weak var toggleFahrenheitOrCelsius: UISegmentedControl!
    
    var gotLocation = false
    var isFahrenheit = true
    let locationManager = LocationManager()
    let weatherManager = WeatherManager()
    
    let gpsLocationManager = CLLocationManager()
    
    var latitude = ""
    var longitude = ""
    var woeid = 0
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        //Setup delegate
        weatherManager.delegate = self
        locationManager.delegate = self
        gpsLocationManager.delegate = self
        //Start GPS
        gpsLocationManager.desiredAccuracy = kCLLocationAccuracyHundredMeters
        gpsLocationManager.requestWhenInUseAuthorization()
        gpsLocationManager.startUpdatingLocation()
        
        //Start Weather
        locationManager.reloadData()
        weatherManager.reloadData(woeid: woeid)
        
        
        // Do any additional setup after loading the view.
    }
    
    @IBAction func refreshButtonTouched(_ sender: UIBarButtonItem) {
        gpsLocationManager.delegate = self
        
        gpsLocationManager.requestLocation()
        initilizeWeather()
        weatherManager.reloadData(woeid: woeid)
        weatherUpdated()
    }
    
    //function to initialize weather
    func initilizeWeather(){
        if gotLocation {
            locationManager.latt = latitude
            locationManager.long = longitude
            locationManager.fetchDataWithLattLong()
            
            
        }else{
            locationManager.reloadData()
            
        }
        
        
    }
    
    @IBAction func toggleTempTouched(_ sender: UISegmentedControl) {
        let selectedSegment = sender.selectedSegmentIndex
        if selectedSegment == 0 {
            isFahrenheit = true
        }else{
            isFahrenheit = false
        }
        weatherUpdated()
    }
    
    
    // MARK: - location gps methods
    func locationManager(_ manager: CLLocationManager, didUpdateLocations locations: [CLLocation]) {
        let location = locations[locations.count - 1]
        if location.horizontalAccuracy > 0{
            gotLocation = true
            gpsLocationManager.stopUpdatingLocation()
            gpsLocationManager.delegate = nil
            //print("longitude \(location.coordinate.longitude) latitude \(location.coordinate.latitude)")
            latitude = "\(location.coordinate.latitude)"
            longitude = "\(location.coordinate.longitude)"
            initilizeWeather()
            
        }
    }
    func locationManager(_ manager: CLLocationManager, didFailWithError error: Error) {
        print(error)
        currentCityLabel.text = "Location Unavailable"
    }
    
    
    
     // MARK: - Navigation
     
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == "showSearch" {
            let destinationVC = segue.destination as! SearchViewController
            destinationVC.delegate = self
        }
 }
    
}//end class

extension WeatherViewController: LocationManagerDelegate {
    
    func locationUpdateFailed() {
        let alert = UIAlertController.init(title: "Sorry...", message: "Your city isn't in our databases yet. Try the next largest city.", preferredStyle: .alert)
        alert.addAction(UIAlertAction.init(title: "Ok", style: .cancel, handler: { (action) in
            self.performSegue(withIdentifier: "showSearch", sender: action)
        }))
        
        
        DispatchQueue.main.async {
            self.present(alert, animated: true, completion: nil)
        }
        
    }
    
    func locationUpdated() {
        if let currentLocation = locationManager.location.first{
            currentCityLabel.text = currentLocation.title
            self.woeid = currentLocation.woeid
            
            weatherManager.reloadData(woeid: woeid)
            weatherUpdated()
        }

    }
    
    
}//end extension metaweather location delegate method


extension WeatherViewController: WeatherManagerDelegate {
    func weatherUpdated() {
        guard let iconImage = weatherManager.weatherAtIndex(0)?.weather_state_abbr else {return}
        let pngImage = "\(iconImage).png"
        let urlString = "https://www.metaweather.com/static/img/weather/png/\(pngImage)"
        currentIconImageView.sd_setImage(with: URL(string: urlString))
        guard let currentWeather = weatherManager.weatherAtIndex(0)?.the_temp else{return}
        currentTemperatureLabel.text = isFahrenheit ? "\(String(format: "%0.2f", currentWeather.toFahrenheit()))°" : "\(String(format: "%0.2f", currentWeather))°"
        self.tableView.reloadData()
        
    }
    
    
} // end weathermanager delegate extension

extension WeatherViewController: UITableViewDelegate, UITableViewDataSource {
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return weatherManager.weather?.count ?? 0
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "weatherCell", for: indexPath) as! WeatherTableViewCell
        
        if let cityWeather = weatherManager.weatherAtIndex(indexPath.row) {
            //(1°C × 9/5) + 32
            cell.maxTemperatureLabel.text = isFahrenheit ? "Max: \(String(format: "%0.2f", cityWeather.max_temp.toFahrenheit()))°" : "Max: \(String(format: "%0.2f", cityWeather.max_temp))°"
            
            cell.minTemperatureLabel.text = isFahrenheit ? "Min: \(String(format: "%0.2f", cityWeather.min_temp.toFahrenheit()))°" : "Min: \(String(format: "%0.2f", cityWeather.min_temp))°"
            
            cell.humidityLabel.text = "Humidity: \(String(cityWeather.humidity))"
            cell.percentChanceLabel.text = "\(String(cityWeather.predictability))%"
            cell.windLabel.text = "Wind: \(String(format: "%0.2f", cityWeather.wind_speed)) \(cityWeather.wind_direction_compass)"
            
            let pngImage = "\(cityWeather.weather_state_abbr).png"
            let urlString = "https://www.metaweather.com/static/img/weather/png/64/\(pngImage)"
            cell.iconImageView.sd_setImage(with: URL(string: urlString), completed: nil)
            
            // TODO: - make a dateformatter function
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
}//end tableview method extonsion

extension WeatherViewController: SearchCityDelegate {
    
    func userEnteredANewCityName(city: String) {
        print("called")
        locationManager.query = city
        locationManager.reloadData()
        locationManager.getWoeid()
        print(city)
        print(locationManager.location.first?.title)
        
    }
    
}//end search delegate





