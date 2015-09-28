/**
 * MMSContent.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.eps.service.sms.client;

public class MMSContent  implements java.io.Serializable {
    private byte[] musicByte;

    private java.lang.String musicType;

    private byte[] imgByte;

    private java.lang.String text;

    private java.lang.String imgType;

    public MMSContent() {
    }

    public MMSContent(
           byte[] musicByte,
           java.lang.String musicType,
           byte[] imgByte,
           java.lang.String text,
           java.lang.String imgType) {
           this.musicByte = musicByte;
           this.musicType = musicType;
           this.imgByte = imgByte;
           this.text = text;
           this.imgType = imgType;
    }


    /**
     * Gets the musicByte value for this MMSContent.
     * 
     * @return musicByte
     */
    public byte[] getMusicByte() {
        return musicByte;
    }


    /**
     * Sets the musicByte value for this MMSContent.
     * 
     * @param musicByte
     */
    public void setMusicByte(byte[] musicByte) {
        this.musicByte = musicByte;
    }


    /**
     * Gets the musicType value for this MMSContent.
     * 
     * @return musicType
     */
    public java.lang.String getMusicType() {
        return musicType;
    }


    /**
     * Sets the musicType value for this MMSContent.
     * 
     * @param musicType
     */
    public void setMusicType(java.lang.String musicType) {
        this.musicType = musicType;
    }


    /**
     * Gets the imgByte value for this MMSContent.
     * 
     * @return imgByte
     */
    public byte[] getImgByte() {
        return imgByte;
    }


    /**
     * Sets the imgByte value for this MMSContent.
     * 
     * @param imgByte
     */
    public void setImgByte(byte[] imgByte) {
        this.imgByte = imgByte;
    }


    /**
     * Gets the text value for this MMSContent.
     * 
     * @return text
     */
    public java.lang.String getText() {
        return text;
    }


    /**
     * Sets the text value for this MMSContent.
     * 
     * @param text
     */
    public void setText(java.lang.String text) {
        this.text = text;
    }


    /**
     * Gets the imgType value for this MMSContent.
     * 
     * @return imgType
     */
    public java.lang.String getImgType() {
        return imgType;
    }


    /**
     * Sets the imgType value for this MMSContent.
     * 
     * @param imgType
     */
    public void setImgType(java.lang.String imgType) {
        this.imgType = imgType;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof MMSContent)) return false;
        MMSContent other = (MMSContent) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.musicByte==null && other.getMusicByte()==null) || 
             (this.musicByte!=null &&
              java.util.Arrays.equals(this.musicByte, other.getMusicByte()))) &&
            ((this.musicType==null && other.getMusicType()==null) || 
             (this.musicType!=null &&
              this.musicType.equals(other.getMusicType()))) &&
            ((this.imgByte==null && other.getImgByte()==null) || 
             (this.imgByte!=null &&
              java.util.Arrays.equals(this.imgByte, other.getImgByte()))) &&
            ((this.text==null && other.getText()==null) || 
             (this.text!=null &&
              this.text.equals(other.getText()))) &&
            ((this.imgType==null && other.getImgType()==null) || 
             (this.imgType!=null &&
              this.imgType.equals(other.getImgType())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getMusicByte() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getMusicByte());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getMusicByte(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getMusicType() != null) {
            _hashCode += getMusicType().hashCode();
        }
        if (getImgByte() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getImgByte());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getImgByte(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getText() != null) {
            _hashCode += getText().hashCode();
        }
        if (getImgType() != null) {
            _hashCode += getImgType().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(MMSContent.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "MMSContent"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("musicByte");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "MusicByte"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "base64Binary"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("musicType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "MusicType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("imgByte");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "ImgByte"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "base64Binary"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("text");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "Text"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("imgType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "ImgType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
