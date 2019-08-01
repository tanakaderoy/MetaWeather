//
//  WeatherNetworkAdapter.swift
//  MetaWeather
//
//  Created by Tanaka Mazivanhanga on 7/31/19.
//  Copyright © 2019 Tanaka Mazivanhanga. All rights reserved.
//

import Foundation
protocol WeatherNetworkAdapterDelegate  {
    func weatherUpdated()
}
class WeatherNetworkAdapter {
    var weather: [ConsolidatedWeather]?
    var delegate: WeatherNetworkAdapterDelegate?
    var woeid: Int?
    
    private var urlString = "https://www.metaweather.com"
    
    
    func fetchWeatherWithWoeid(woeid: Int){
        weather = [ConsolidatedWeather]()
        
        var urlComponents = URLComponents(string: urlString)
        
        urlComponents?.path = "/api/location/\(woeid)"
        //print(urlComponents?.url!)
        
        
        guard let url = urlComponents?.url else { fatalError("URL is wrong")}
        
        let session = URLSession.shared
        
        let task = session.dataTask(with: url) { (data, response, error) in
            
            if error != nil || data == nil {
                print("Error handle later")
                return
            }
            
            
            guard let response = response as? HTTPURLResponse, (200...299).contains(response.statusCode) else {
                print("error in the response weather")
                return
            }
            
            
            if let data = data {
                //print(String(data: data, encoding: .utf8)!)
                let jsonDecoder = JSONDecoder()
                
                do {
                    let weatherRoot = try jsonDecoder.decode(WeatherRoot.self, from: data)
                    self.weather?.append(contentsOf: weatherRoot.consolidatedWeather)
                    
                    DispatchQueue.main.async {
                        self.delegate?.weatherUpdated()
                    }
                }
                catch let error {
                    print("error parsing json: \(error.localizedDescription)")
                    
                }
            }
        }
        
        task.resume()
}

}
