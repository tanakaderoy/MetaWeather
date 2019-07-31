//
//  Util.swift
//  MetaWeather
//
//  Created by Tanaka Mazivanhanga on 7/31/19.
//  Copyright © 2019 Tanaka Mazivanhanga. All rights reserved.
//

import Foundation
extension Array {
    func isValidIndex(_ index: Int) -> Bool {
        return index >= 0 && index < self.count
    }
}
