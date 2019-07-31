//
//  LocationManager.swift
//  MetaWeather
//
//  Created by Tanaka Mazivanhanga on 7/31/19.
//  Copyright © 2019 Tanaka Mazivanhanga. All rights reserved.
//

import Foundation

protocol LocationManagerDelegate {
    func locationUpdated()
}

class LocationManager {
    var location = [Location]()
    var delegate: LocationManagerDelegate?
    private var locationNetworkAdapter: LocationNetworkAdapter!
    var query: String?
    
    init() {
        locationNetworkAdapter = LocationNetworkAdapter()
        locationNetworkAdapter.delegate = self
    }
    func reloadData() {
        if let query = query{
        locationNetworkAdapter.fetchWeatherWithQuery(query: query)
    }
    }
    

}

extension LocationManager: LocationNetworkAdapterDelegate{
    func locationUpdated() {
        self.location = locationNetworkAdapter.location!
        if let query = locationNetworkAdapter.query{
            self.query = query
        }
        delegate?.locationUpdated()
    }
    
    
}
