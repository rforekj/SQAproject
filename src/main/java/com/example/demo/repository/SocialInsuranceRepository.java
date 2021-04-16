package com.example.demo.repository;

import com.example.demo.model.SocialInsurance;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface SocialInsuranceRepository extends CrudRepository<SocialInsurance, Long> {
    List<SocialInsurance> findAllByExpiredDateIsBeforeAndPaidIsFalse(Date date);

    List<SocialInsurance> findAllByExpiredDateIsAfterAndPaidIsFalse(Date date);

    List<SocialInsurance> findAllByExpiredDateIsBetweenAndPaidIsFalse(Date before, Date after);

    List<SocialInsurance> findAllByPaidIsTrue();

    List<SocialInsurance> findAllByPaidIsTrueAndExpiredDateIsBetween(Date before, Date after);

    Integer countAllByExpiredDateBeforeAndPaidIsFalse(Date date);

    Integer countAllByExpiredDateAfterAndPaidIsFalse(Date date);

    Integer countAllByExpiredDateIsBetweenAndPaidIsFalse(Date before, Date after);

    Integer countAllByPaidIsTrueAndExpiredDateIsBetween(Date before, Date after);

    @Query(value = "SELECT sum(t.price*c.salary) FROM social_insurance_type t join (social_insurance i join client c on i.client_id = c.id)  on t.id = i.type_id  where i.expired_date > :start_date and i.expired_date < :end_date and i.paid=false", nativeQuery = true)
    Double calculateMoneyHaveNotPaid(@Param("start_date") Date startDate, @Param("end_date") Date endDate);

    @Query(value = "SELECT sum(t.price*c.salary) FROM social_insurance_type t join (social_insurance i join client c on i.client_id = c.id)  on t.id = i.type_id  where i.expired_date > :date and i.paid=false", nativeQuery = true)
    Double calculateMoneyHavePaid(@Param("date") Date date);
}
