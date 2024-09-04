package com.josefigueroa.soccerfieldmanager.services;

import com.josefigueroa.soccerfieldmanager.models.User;

public interface IAuthService  {
    public User save(User user);

    public User login(String email);
}
