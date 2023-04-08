package com.shipogle.app.service;

import com.shipogle.app.model.DriverRoute;
import com.shipogle.app.model.Rating;
import com.shipogle.app.model.User;
import com.shipogle.app.repository.DriverRouteRepository;
import com.shipogle.app.repository.RatingRepository;
import com.shipogle.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingServiceImpl implements RatingService {
    @Autowired
    RatingRepository ratingRepo;

    @Autowired
    UserRepository userRepo;

    @Autowired
    DriverRouteRepository driverRouteRepo;
    @Autowired
    UserService userService;

    @Override
    public String storeRating(Long driver_route_id, float star, String review){
        try {
            Rating rating = new Rating();
            DriverRoute driverRoute = driverRouteRepo.getDriverRouteById(driver_route_id);

//            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//            String user_email = auth.getPrincipal().toString();
//
//            User sender = userRepo.getUserByEmail(user_email);

            User sender = userService.getLoggedInUser();

            if(driverRoute==null || sender==null)
                return "Not able to post rating";

            rating.setUser(sender);
            rating.setDriverRoute(driverRoute);
            rating.setStar(star);
            rating.setReview(review);

            ratingRepo.save(rating);

        }catch (Exception e){
            return e.getMessage();
        }
        return "Rating is posted";
    }

    @Override
    public String deleteRating(Integer rating_id){
        try {
            Rating rating = ratingRepo.getRatingById(rating_id);

            ratingRepo.delete(rating);

        }catch (Exception e){
            return e.getMessage();
        }
        return "Rating deleted";
    }

    @Override
    public List<Rating> getDelivererRating(){
        try {
//            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//            String user_email = auth.getPrincipal().toString();
//
//            User deliverer = userRepo.getUserByEmail(user_email);

            User deliverer = userService.getLoggedInUser();

            List<Rating> ratings = ratingRepo.getAllByDriverRoute_DriverId(Long.valueOf(deliverer.getUser_id()));

            return ratings;

        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Rating> getSenderPostedRating(){
        try {
//            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//            String user_email = auth.getPrincipal().toString();
//
//            User sender = userRepo.getUserByEmail(user_email);

            User sender = userService.getLoggedInUser();

            List<Rating> ratings = ratingRepo.getAllByUser_Id(sender.getUser_id());

            return ratings;

        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
}
