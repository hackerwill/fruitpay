package com.fruitpay.comm.session.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.fruitpay.comm.auth.LoginConst;
import com.fruitpay.comm.session.FPSessionUtil;
import com.fruitpay.comm.session.model.FPSessionFactory;
import com.fruitpay.comm.utils.StringUtil;

public class ClearSessionServlet extends HttpServlet {
	private final Logger log = Logger.getLogger(ClearSessionServlet.class);

	public ClearSessionServlet() {
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doProcess(req, resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doProcess(req, resp);
	}

	protected void doProcess(HttpServletRequest pReq, HttpServletResponse pResp) throws ServletException, IOException {

		if (pReq.getProtocol().equals("HTTP/1.0")) { // HTTP/1.0的話
			pResp.setHeader("Pragma", "no-cache");
		} else if (pReq.getProtocol().equals("HTTP/1.1")) {
			pResp.setHeader("Cache-Control", "no-cache");
		}

		/*** get user token from Header **/
		String FPToken = FPSessionUtil.getHeader(pReq, LoginConst.LOGIN_AUTHORIZATION);
		String thirdParty = pReq.getParameter(LoginConst.LOGIN_THIRD_PARTY_LOGOUT);

		if (!StringUtil.isEmptyOrSpace(FPToken)) {
			try {
				// HttpSession session = pReq.getSession(false);
				// if(session != null)
				// session.invalidate();
				// for(int i = 0; i < pReq.getCookies().length; i++) {
				// pReq.getCookies()[i].setMaxAge(0);
				// }
				// Map<String,String> paramMap = new HashMap<String,String>();
				// paramMap.put(LoginConst.FP_SESSION_SECURITY_KEY, FPToken);
				log.debug("FPToken==[" + FPToken + "]");
				log.debug("=======================FP Session Logout Start=======================");
				FPSessionFactory.getInstance().getFPSessionMap().remove(FPToken);
				log.debug("=======================FP Session Logout End=======================");
			} catch (Exception e) {
				log.error("ClearSessionServlet Fail", e);
			}
		}
	}
}
