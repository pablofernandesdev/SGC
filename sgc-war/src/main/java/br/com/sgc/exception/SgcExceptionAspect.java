package br.com.sgc.exception;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;

import br.com.sgc.util.Mensagens;

public class SgcExceptionAspect {

	private Logger logger = Logger.getLogger(SgcExceptionAspect.class);

	//@Around("execution(* *..visao.*Visao.*(..))")
	public Object visaoExceptions(ProceedingJoinPoint call) throws Throwable {
		Object o = null;
		try {
			o = call.proceed();
		} catch (NegocioException e) {
			throw new NegocioException(e.getMessage(), e.getArgs());
		} catch (PersistenciaException e) {
			logger.error(e.getMessage(), e);
			Mensagens.addError("erro.banco.dados");
		} catch (ServiceException e) {
			logger.error(e.getMessage(), e);
			Mensagens.addError("erro.interno");
		} catch (Exception e) {
			logger.error("Erro VisaoExceptionAspecto: " + call, e);
			Mensagens.addError("erro.interno");
		}
		return o;
	}

	//@Around("execution(* *..service.impl.*ServiceImpl.*(..))")
	public Object serviceExceptions(ProceedingJoinPoint call) throws Throwable {
		Object o = null;
		try {
			o = call.proceed();
		} catch (NegocioException e) {
			throw new NegocioException(e.getMessage(), e.getArgs());
		} catch (PersistenciaException e) {
			throw new PersistenciaException(e.getMessage(), e);
		} catch (Exception e) {
			throw new ServiceException("Erro ServiceExceptionAspecto: " + call,e);
		}
		return o;
	}

	//@Around("execution(* *..dao.impl.*DaoImpl.*(..))")
	public Object daoExceptions(ProceedingJoinPoint call) throws Throwable {
		Object o = null;
		try {
			o = call.proceed();
		} catch (NegocioException e) {
			throw new NegocioException(e.getMessage(), e.getArgs());
		} catch (Exception e) {
			throw new PersistenciaException("Erro PersistenciaExceptionAspecto: " + call, e);
		}
		return o;
	}


}
