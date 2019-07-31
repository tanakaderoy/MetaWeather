//
//  LocationNetworkAdapter.swift
//  MetaWeather
//
//  Created by Tanaka Mazivanhanga on 7/31/19.
//  Copyright © 2019 Tanaka Mazivanhanga. All rights reserved.
//

import Foundation
protocol LocationNetworkAdapterDelegate  {
    func locationUpdated()
}
class LocationNetworkAdapter {
    var location: [Location]?
    var delegate: LocationNetworkAdapterDelegate?
    var query: String?
    
    private var urlString = "https://www.metaweather.com/api/location/search/?"
    
    
    func fetchWeatherWithQuery(query: String){
        
        var urlComponents = URLComponents(string: urlString)
        
        let queryItems = [URLQueryItem(name: "query", value: "\(query)")]
        
        urlComponents?.queryItems = queryItems
        
        guard let url = urlComponents?.url else { fatalError("URL is wrong")}
        
         let session = URLSession.shared
        
        let task = session.dataTask(with: url) { (data, response, error) in
            
            if error != nil || data == nil {
                print("Error handle later")
                return
            }
            
            
            guard let response = response as? HTTPURLResponse, (200...299).contains(response.statusCode) else {
                print("error in the response")
                return
            }
            
            
            if let data = data {
                print(String(data: data, encoding: .utf8))
                let jsonDecoder = JSONDecoder()
                
                do {
                    self.location = try jsonDecoder.decode([Location].self, from: data)
                    
                    DispatchQueue.main.async {
                        self.delegate?.locationUpdated()
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