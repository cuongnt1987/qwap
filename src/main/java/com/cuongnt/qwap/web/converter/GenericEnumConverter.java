/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuongnt.qwap.web.converter;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Cuong
 */
@FacesConverter("enumConverter")
public class GenericEnumConverter implements Converter{
    public static final String ATTRIBUTE_ENUM_TYPE = "GenericEnumConverter.enumType";

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        Class<Enum> enumType = (Class<Enum>) component.getAttributes().get(ATTRIBUTE_ENUM_TYPE);
        
        try {
            if (value == null) return null;
            return Enum.valueOf(enumType, value);
        } catch (IllegalArgumentException e) {
            throw new ConverterException(new FacesMessage("Value is not enum of type " + enumType));
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value instanceof Enum) {
            component.getAttributes().put(ATTRIBUTE_ENUM_TYPE, value.getClass());
            return ((Enum<?>) value).name();
        } else {
            if (value == null || (value instanceof String && String.valueOf(value).trim().isEmpty())) {
                return null;
            }
            throw new ConverterException(new FacesMessage("Value is not of enum" + value));
        }
    }
    
}