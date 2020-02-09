/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tomtom.personalLibrary.service;

import com.tomtom.personalLibrary.data.UnitOfWork;
import org.springframework.stereotype.Service;

/**
 *
 * @author TomTom
 */
@Service
public class ChallengeService {
    
    UnitOfWork unitOfWork;
    
    public ChallengeService(UnitOfWork uow){
        this.unitOfWork = uow;
    }
}
