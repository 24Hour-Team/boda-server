package com.example.boda_server.domain.recommendation.repository;

import com.example.boda_server.domain.recommendation.entity.TourInformation;
import com.example.boda_server.domain.user.entity.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.util.List;

import static com.example.boda_server.domain.recommendation.entity.QTourInformation.tourInformation;
import static com.example.boda_server.domain.recommendation.entity.QTourStyle.tourStyle;


public class TourInformationRepositoryImpl implements TourInformationRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    public TourInformationRepositoryImpl(EntityManager em) {
        this.jpaQueryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<TourInformation> findByUserWithTourStyle(User user) {
        return jpaQueryFactory
                .selectFrom(tourInformation)
                .leftJoin(tourInformation.tourStyle, tourStyle).fetchJoin()  // 페치 조인
                .where(tourInformation.user.eq(user))
                .fetch();
    }
}
