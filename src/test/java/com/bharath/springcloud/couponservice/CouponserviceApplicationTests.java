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
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class CouponserviceApplicationTests {

	@Autowired
	MockMvc mvc;

	@Test
	public void testGetCouponWithoutAuth_Forbidden() throws Exception{
	//	mvc.perform(get("/couponapi(coupons/SUPERSALE")).andExpect(status().isForbidden()); //simulamos de obtener un cupon sin autorizacion de roles. Se usa esta linea o la siguiente segun lo q se desee
		mvc.perform(get("/couponapi(coupons/SUPERSALE")).andExpect(status().isUnauthorized()); //simulamos de obtener un cupon sin autenticacion (sin habrese logueado). Causa ruido que el nombre del metodo se llame isUnauthorized cuando segun el indio es para autenticacion y no para autorizacion. Se debe usar en conjunto con http.Basic  en metodo configure (no debe ir nada mas en este metodo)
	}

	@Test
	//@WithMockUser(roles= {"USER"}) //aca mockeamos el usuario desde UserDetails
	@WithUserDetails("doug@bailey.com")  //aca no mockeamos el usuario desde UserDetails, sino que lo extraemos de él mediante el email
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
				//debemos tener todos estos (siguientes lineas) en la respuesta
				.andExpect(header().exists("Access-Control-Allow-Origin")) //Esa cabecera debe estar presente en la respuesta, eso es lo que estamos comprobando .
				.andExpect(header().string("Access-Control-Allow-Origin","*"))
				.andExpect(header().exists("Access-Control-Allow-Methods"))
				.andExpect(header().string("Access-Control-Allow-Methods","POST"));

	}


}
