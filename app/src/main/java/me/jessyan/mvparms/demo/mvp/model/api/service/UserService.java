package me.jessyan.mvparms.demo.mvp.model.api.service;

import java.util.List;

import io.reactivex.Observable;
import me.jessyan.mvparms.demo.mvp.model.entity.User;
import me.jessyan.mvparms.demo.mvp.model.entity.UserDetail;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * 存放关于用户的一些api
 * Created by jess on 8/5/16 12:05
 * contact with jess.yan.effort@gmail.com
 */
public interface UserService {

    String HEADER_API_VERSION = "Accept: application/vnd.github.v3+json";

    @Headers({HEADER_API_VERSION})
    @GET("/users")
    Observable<List<User>> getUsers(@Query("since") int lastIdQueried, @Query("per_page") int perPage);

    /**
     * Method: getUserDetail
     * Description: Get a single user
     * https://developer.github.com/v3/users/#get-a-single-user
     * Date: 2017/5/17 22:44
     * Add by xiaobailong24
     */
    @Headers({HEADER_API_VERSION})
    @GET("/users/{username}")
    Observable<UserDetail> getUserDetail(@Path("username") String username);


}
