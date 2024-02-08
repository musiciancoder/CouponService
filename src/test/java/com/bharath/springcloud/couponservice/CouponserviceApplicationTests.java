package com.bharath.springcloud.couponservice;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.web.servlet.function.RequestPredicates.contentType;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class CouponserviceApplicationTests {

	@Autowired
	MockMvc mvc;

	@Test
	public void testGetCouponWithoutAuth_Forbidden() throws Exception{
		mvc.perform(get("/couponapi(coupons/SUPERSALE")).andExpect(status().isForbidden()); //simulamos de obtener un cupon sin autorizacion de roles
	}

	@Test
	@WithMockUser(roles= {"USER"})
	public void testGetCouponWithoutAuth_Success() throws Exception{
		mvc.perform(get("/couponapi(coupons/SUPERSALE")).andExpect(status().isOk()); //simulamos de obtener un cupon con autorizacion de roles

	}

	@Test
	@WithMockUser(roles= {"ADMIN"})
	public void testCreateCouponWithoutCSRF_Forbidden() throws Exception {
		mvc.perform(post("/couponapi/coupons")
				.content("{\"code":\"SUPERSALECSRF\",\"discount\":80.000,\"espDate\":\"12/12/2020\"}")
		.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isForbidden()); //revisar los iport de la clase anterior, me dio lata hacerlo

	}

	@Test
	@WithMockUser(roles= {"ADMIN"})
	public void testCreateCouponWithCSRF_Forbidden() throws Exception {
		mvc.perform(post("/couponapi/coupons")
				.content("{\"code":\"SUPERSALECSRF\",\"discount\":80.000,\"espDate\":\"12/12/2020\"}")
		.contentType(MediaType.APPLICATION_JSON).with(csrf().asHeader())).andExpect(status().isOk());

	}

	//este usuario tiene los permisos de CSRF pero la idea es q falle porque no tiene lor "ADMIN" para crear un cupon
	@Test
	@WithMockUser(roles= {"USER"})
	public void testCreateCoupon_NonAdminUser_Forbidden() throws Exception {
		mvc.perform(post("/couponapi/coupons")
				.content("{\"code":\"SUPERSALECSRF\",\"discount\":80.000,\"espDate\":\"12/12/2020\"}")
		.contentType(MediaType.APPLICATION_JSON).with(csrf().asHeader())).andExpect(status().isForbidden());

	}


	//CORS
	@Test
	@WithMockUser(roles= {"USER"})
	public void testCORS() throws Exception {
		mvc.perform(options("/couponapi/coupons").header("Access-Control-Request-Method","POST").header("Origin",
				"http://www.bharath.com")).andExpect(status().isOk())
				.andExpect(header().exists("Access-Control-Allow-Origin"))
				.andExpect(header().string("Access-Control-Allow-Origin","*"))
				.andExpect(header().exists("Access-Control-Allow-Methods"))
				.andExpect(header().string("Access-Control-Allow-Methods","POST"));

	}


}
