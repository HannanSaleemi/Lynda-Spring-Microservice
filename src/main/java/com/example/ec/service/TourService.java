package com.example.ec.service;

import com.example.ec.domain.Difficulty;
import com.example.ec.domain.Region;
import com.example.ec.domain.Tour;
import com.example.ec.domain.TourPackage;
import com.example.ec.repo.TourPackageRepository;
import com.example.ec.repo.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TourService {
    private TourRepository tourRepository;
    private TourPackageRepository tourPackageRepository;

    @Autowired
    public TourService(TourRepository tourRepository, TourPackageRepository tourPackageRepository) {
        this.tourRepository = tourRepository;
        this.tourPackageRepository = tourPackageRepository;
    }

    /**
     * Create a new Tour Object and persist it to the Database.
     * <p>
     * Before we create a tour - we need a tourpackagae for it
     *
     * @param title           title
     * @param description     description
     * @param blurb           blurb
     * @param price           price
     * @param duration        duration
     * @param bullets         comma-separated bullets
     * @param keywords        keywords
     * @param tourPackageName tour package name
     * @param difficulty      difficulty
     * @param region          region
     * @return Tour Entity
     */
    public Tour createTour(String title, String description, String blurb, Integer price,
                           String duration, String bullets,
                           String keywords, String tourPackageName, Difficulty difficulty, Region region) {

        // Check if TourPackage already exists
        // If not create it
        TourPackage tourPackage = tourPackageRepository.findById(tourPackageName)
                .orElseThrow(() -> new RuntimeException("Tour Package does not exist: " + tourPackageName));

        // If we find a tourpackage
        // We create a tour in the DB
        return tourRepository.save(new Tour(title, description, blurb, price, duration, bullets, keywords, tourPackage, difficulty, region));
    }

    /**
     * Calculate the number of Tours in the Database.
     *
     * @return the total.
     */
    public long total() {
        return tourRepository.count();
    }
}