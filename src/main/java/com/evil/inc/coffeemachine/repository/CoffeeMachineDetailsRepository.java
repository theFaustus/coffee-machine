package com.evil.inc.coffeemachine.repository;

import com.evil.inc.coffeemachine.domain.CoffeeMachineDetails;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoffeeMachineDetailsRepository extends CrudRepository<CoffeeMachineDetails, String> {}