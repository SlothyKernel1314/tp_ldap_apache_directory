package com.iat.tpldapapachedirectory.web;

import com.iat.tpldapapachedirectory.configuration.GlobalProperties;
import com.iat.tpldapapachedirectory.dao.UserDao;
import com.iat.tpldapapachedirectory.model.User;
import com.iat.tpldapapachedirectory.service.ConnectionService;
import org.apache.directory.api.ldap.model.cursor.CursorException;
import org.apache.directory.api.ldap.model.exception.LdapException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

@Controller
public class UserController {

    @Autowired
    ConnectionService connectionService;

    @Autowired
    GlobalProperties globalProperties;

    @Autowired
    UserDao userDao;

    @GetMapping("/admList")
    public String admList(Model model) throws IOException, LdapException, CursorException {
        Iterable<User> admins = userDao.getAllAdmByIdAndName();
        model.addAttribute("admins", admins);
        model.addAttribute("msg", "Ceci est un test");
        return "admList";
    }

}
