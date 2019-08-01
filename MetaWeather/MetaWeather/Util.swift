//
//  Util.swift
//  MetaWeather
//
//  Created by Tanaka Mazivanhanga on 7/31/19.
//  Copyright © 2019 Tanaka Mazivanhanga. All rights reserved.
//

import Foundation
//if an index is valid for the array
extension Array {
    func isValidIndex(_ index: Int) -> Bool {
        return index >= 0 && index < self.count
    }
}
//trim whitespace from string beggining and end
extension String {
    func trim() -> String
    {
        return self.trimmingCharacters(in: CharacterSet.whitespaces)
    }
}
//turns celcius to fahrenheit
extension Double {
    func toFahrenheit() -> Double {
        return self * 9 / 5 + 32
    }
    
}
