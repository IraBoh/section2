package com.eazybytes.cards.service;

import com.eazybytes.cards.dto.CardsDto;

public interface ICardsService {

    /**
     * @param mobileNumber of the Customer
     */
    void  createCard(String mobileNumber);

    /**
     * @param mobileNumber of the Customer
     * @return Card Details based on a given mobileNumber
     */
   CardsDto fetchCard(String mobileNumber);

    /**
     * @param cardsDto Object
     * @return boolean indicating if the update of card details is successful or not
     */
   boolean updateCard(CardsDto cardsDto);

    /**
     * @param mobileNumber
     * @return boolean indicating if the delete of card details is successful or not
     */
   boolean deleteCard(String mobileNumber);

}
