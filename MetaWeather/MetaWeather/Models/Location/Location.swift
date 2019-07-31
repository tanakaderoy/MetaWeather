//
//  Location.swift
//  MetaWeather
//
//  Created by Tanaka Mazivanhanga on 7/31/19.
//  Copyright © 2019 Tanaka Mazivanhanga. All rights reserved.
//

import Foundation
class Location: Codable {
    
    let title: String
    let location_type: String
    let woeid: Int
    let latt_long: String
    
    
    enum CodingKeys: String, CodingKey {
        case title = "title"
        case location_type = "location_type"
        case woeid = "woeid"
        case latt_long = "latt_long"
    }
    
}
