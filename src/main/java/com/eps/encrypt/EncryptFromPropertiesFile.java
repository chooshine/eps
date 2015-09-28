package com.eps.encrypt;

import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class EncryptFromPropertiesFile extends PropertyPlaceholderConfigurer{
	private final static String[] ENCRYPTKEYS = {"jdbc.userName","jdbc.password","mail.server.password"};
	@SuppressWarnings("rawtypes")
	@Override
	protected void processProperties(
			ConfigurableListableBeanFactory beanFactoryToProcess,
			Properties props) throws BeansException {
		
		Set p = props.keySet();
		for (Iterator iterator = p.iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			if(ArrayUtils.contains(ENCRYPTKEYS, key)){
				Encrypt_Des des = new Encrypt_Des();
				des.initKey(Encrypt_Des.GENERATEKEYSTR);
				String mingValue = des.getDesString(props.getProperty(key));
				props.setProperty(key, mingValue);
			}
		}
		super.processProperties(beanFactoryToProcess, props);
	}
}
