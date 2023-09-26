package com.crud.basico.crud.security.Util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.crud.basico.crud.security.entity.Role;
import com.crud.basico.crud.security.enums.RoleNombre;
import com.crud.basico.crud.security.service.RoleService;

@Component
public class CreateRoles implements CommandLineRunner{



	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		
	}



	/*@Autowired
	RoleService roleService;
	
	@Override
	public void run(String... args) throws Exception {
		Role roleAdmin = new Role(RoleNombre.ROLE_ADMIN);
		Role roleUser = new Role(RoleNombre.ROLE_USER);
		System.out.println("entra y se  ejecuta");
		roleService.save(roleAdmin);
		
		roleService.save(roleUser);
		
		
	}
*/


}
