package ceprobi.scdd.viewcontroller;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import ceprobi.scdd.daosession.UsuarioSessionDAO;
import ceprobi.scdd.encriptar.EncriptaDesencripta;
import ceprobi.scdd.model.ScddUsuario;
import ceprobi.scdd.repository.UserRepository;

@RestController
public class VistasController {
	
	@Autowired
	UserRepository userRepository;
	
	@Value("${ceprobi.key}")
	private String llaveCeprobi;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(VistasController.class);
	private static final String VIEW_INDEX_PAGE_CEPROBI = "/vistaprincipal/index";
	private static final String VIEW_CAPTURA_PAGE = "/usrcapturasolicitudes/capturadatos";
	private static final String VIEW_GERENTE_PAGE = "/gerentesolicitudes/gerenteSolicitudes";
	private static final String VIEW_ADMIN_PAGE = "/adminsolicitudes/administradorSolicitudes";
	
	@GetMapping("menu")
	public ModelAndView vistaPricipal() {
		return new ModelAndView(VIEW_INDEX_PAGE_CEPROBI);
	}
	@GetMapping("/")
	public ModelAndView vistaPricipalDos() {
		return new ModelAndView(VIEW_INDEX_PAGE_CEPROBI);
	}
	
	@GetMapping("construccion")
	public ModelAndView sitioEnConstruccion() {
		return new ModelAndView("/vistaprincipal/en_construccion");
	}
	
	@GetMapping("AutUsuario")
	public ModelAndView recargarPagina(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		
		HttpSession sessionActiva= (HttpSession) request.getSession();
		UsuarioSessionDAO usuarioSessionActiva = (UsuarioSessionDAO) sessionActiva.getAttribute("session");
		
		String view = "";
		
		if (usuarioSessionActiva == null) {
			mav.setViewName(VIEW_INDEX_PAGE_CEPROBI);
			return mav;
		} else {
			view = usuarioSessionActiva.getTxtRolUsr().equals("USER_CAPTURA") ? VIEW_CAPTURA_PAGE : 
				usuarioSessionActiva.getTxtRolUsr().equals("USER_ADMIN") ? VIEW_ADMIN_PAGE : 
					usuarioSessionActiva.getTxtRolUsr().equals("USER_GRAL") ? VIEW_GERENTE_PAGE : 
					VIEW_INDEX_PAGE_CEPROBI;
		}
		mav.setViewName(view);
		mav.addObject("session", usuarioSessionActiva);
		return mav;
	}
	
	@PostMapping("AutUsuario")
	public ModelAndView obtenerVistaTipoUsuario(
			@RequestParam(required = false, name = "txtNickName") String nickName, 
			@RequestParam(required = false, name = "passUser") String passUser, 			
			HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		EncriptaDesencripta security = new EncriptaDesencripta();
		String view="";
		String pwd = "";
		
		HttpSession sessionActiva= (HttpSession) request.getSession();
		UsuarioSessionDAO usuarioSessionActiva = (UsuarioSessionDAO) sessionActiva.getAttribute("session");
		
		if(usuarioSessionActiva != null) {
			view = usuarioSessionActiva.getTxtRolUsr().equals("USER_CAPTURA") ? VIEW_CAPTURA_PAGE : 
				usuarioSessionActiva.getTxtRolUsr().equals("USER_ADMIN") ? VIEW_ADMIN_PAGE : 
					usuarioSessionActiva.getTxtRolUsr().equals("USER_GRAL") ? VIEW_GERENTE_PAGE : 
					VIEW_INDEX_PAGE_CEPROBI;
			mav.setViewName(view);
			mav.addObject("session", usuarioSessionActiva);
			return mav;
		}
				
		LOGGER.info("Validando al usuario : " + nickName);
		if((passUser.trim().equals("") || passUser == null) && (nickName.trim().equals("") || nickName == null)) {
			mav.addObject("usrInexistente", true);
			mav.setViewName(VIEW_INDEX_PAGE_CEPROBI);
			return mav;
		}
		ScddUsuario usuarioActivo = userRepository.obtieneDatosPorNikName(nickName.trim());
		if(usuarioActivo == null) {
			mav.addObject("usrInexistente", true);
			mav.setViewName(VIEW_INDEX_PAGE_CEPROBI);
			return mav;
		}
		if(usuarioActivo.getIntActivo() == 1) {
			mav.addObject("sessionActiva", true);
			mav.setViewName(VIEW_INDEX_PAGE_CEPROBI);
			return mav;
		}
		try {
			pwd = security.encripta(passUser.trim(), llaveCeprobi);
			LOGGER.info("Encriptador OK");
			/**
			 * PARA CREAR UN USUARIO Y ENCRIPTAR SU CONTRASEÑA
			 * System.out.println("Contraseña a encriptar : " + security.encripta("pdw2Creador", llaveCeprobi));
			 * */
		} catch (Exception e) {
			LOGGER.info("Error al encriptar, valida con el administrador");
			e.printStackTrace();
			mav.addObject("booleanErrorGral", true);
			mav.setViewName(VIEW_INDEX_PAGE_CEPROBI);
			return mav;
		}
		
		ScddUsuario usuarioExistente = userRepository.obtieneDatosPorNikName(nickName.trim());
		ScddUsuario passOk = userRepository.verificaContraValida(pwd);
		
		if(usuarioExistente.getIntNoEmpleado() != passOk.getIntNoEmpleado()) {
			mav.addObject("usrInexistente", true);
			mav.setViewName(VIEW_INDEX_PAGE_CEPROBI);
			return mav;
		} else {
			view = usuarioExistente.getTxtRolUsr().equals("USER_CAPTURA") ? VIEW_CAPTURA_PAGE : 
				usuarioExistente.getTxtRolUsr().equals("USER_ADMIN") ? VIEW_ADMIN_PAGE : 
				usuarioExistente.getTxtRolUsr().equals("USER_GRAL") ? VIEW_GERENTE_PAGE : 
					VIEW_INDEX_PAGE_CEPROBI;
		}
		
		HttpSession miSession = request.getSession(true);
		LOGGER.info("Se creo la session con id : " + miSession.getId());
		
		UsuarioSessionDAO usuario = new UsuarioSessionDAO(nickName.trim(),true,miSession.getId().trim(),usuarioExistente.getTxtRolUsr(), usuarioExistente.getIntNoEmpleado(), usuarioExistente.getTxtAreaAdscripcion() );
		
		/**Agregar al objeto session los valores*/
		miSession.setAttribute("session", usuario);
		/**Envia los valores a la vista*/
		mav.addObject("session", miSession);
		
		Integer respuesta = userRepository.actualizaUsuarioActivo(1, nickName.trim(),passOk.getIntNoEmpleado());
		
		mav.setViewName(view);
		return mav;
	}
	
	@GetMapping("logout")
	public ModelAndView logoutsession(HttpServletRequest request) {
		HttpSession misession= (HttpSession) request.getSession();
		UsuarioSessionDAO usuario= (UsuarioSessionDAO) misession.getAttribute("session");
		LOGGER.info("Invalida session :" + usuario.getIdSession());
		request.getSession().invalidate();
		Integer respuesta = userRepository.actualizaUsuarioActivo(0, usuario.getTxtNickNameUser(), usuario.getnEmpleado());
		ModelAndView mav = new ModelAndView(VIEW_INDEX_PAGE_CEPROBI);
		mav.addObject("booleanLogout", true);
		return mav;
	}
	
}
