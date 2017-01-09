<?xml version="1.0" encoding="utf-8"?>
<wsdl:definitions xmlns:s="http://www.w3.org/2001/XMLSchema" xmlns:soap12="http://schemas.xmlsoap.org/wsdl/soap12/" xmlns:mime="http://schemas.xmlsoap.org/wsdl/mime/" xmlns:tns="http://tempuri.org/" xmlns:soap="http://schemas.xmlsoap.org/wsdl/soap/" xmlns:tm="http://microsoft.com/wsdl/mime/textMatching/" xmlns:http="http://schemas.xmlsoap.org/wsdl/http/" xmlns:soapenc="http://schemas.xmlsoap.org/soap/encoding/" targetNamespace="http://tempuri.org/" xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">
  <wsdl:types>
    <s:schema elementFormDefault="qualified" targetNamespace="http://tempuri.org/">
      <s:element name="QueryBudget">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="buget" type="tns:ArrayOfBudgetVO" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="ArrayOfBudgetVO">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="unbounded" name="BudgetVO" nillable="true" type="tns:BudgetVO" />
        </s:sequence>
      </s:complexType>
      <s:complexType name="BudgetVO">
        <s:sequence>
          <s:element minOccurs="1" maxOccurs="1" name="OADATE" type="s:dateTime" />
          <s:element minOccurs="0" maxOccurs="1" name="KOSTL" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="KSTAR" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="GSBER" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="ANLKL" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="CURRENCY" type="s:string" />
          <s:element minOccurs="1" maxOccurs="1" name="AMOUNT" type="s:decimal" />
          <s:element minOccurs="0" maxOccurs="1" name="EXECTYPE" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="OPTTYPE" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="OAKey" type="s:string" />
          <s:element minOccurs="1" maxOccurs="1" name="STAFFID" type="s:int" />
          <s:element minOccurs="1" maxOccurs="1" name="COMPID" type="s:int" />
          <s:element minOccurs="1" maxOccurs="1" name="DEPTID" type="s:int" />
          <s:element minOccurs="0" maxOccurs="1" name="YYYY" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="MM" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="GPKEY" type="s:string" />
          <s:element minOccurs="1" maxOccurs="1" name="AMOUNT0" type="s:decimal" />
          <s:element minOccurs="1" maxOccurs="1" name="AMOUNT1" type="s:decimal" />
          <s:element minOccurs="1" maxOccurs="1" name="AMOUNT2" type="s:decimal" />
          <s:element minOccurs="1" maxOccurs="1" name="AMOUNT3" type="s:decimal" />
          <s:element minOccurs="0" maxOccurs="1" name="MSGTYP" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="MSGTXT" type="s:string" />
        </s:sequence>
      </s:complexType>
      <s:element name="QueryBudgetResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="QueryBudgetResult" type="tns:ArrayOfBudgetVO" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="UpdateBudget">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="buget" type="tns:ArrayOfBudgetVO" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="UpdateBudgetResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="UpdateBudgetResult" type="tns:ArrayOfBudgetVO" />
          </s:sequence>
        </s:complexType>
      </s:element>
    </s:schema>
  </wsdl:types>
  <wsdl:message name="QueryBudgetSoapIn">
    <wsdl:part name="parameters" element="tns:QueryBudget" />
  </wsdl:message>
  <wsdl:message name="QueryBudgetSoapOut">
    <wsdl:part name="parameters" element="tns:QueryBudgetResponse" />
  </wsdl:message>
  <wsdl:message name="UpdateBudgetSoapIn">
    <wsdl:part name="parameters" element="tns:UpdateBudget" />
  </wsdl:message>
  <wsdl:message name="UpdateBudgetSoapOut">
    <wsdl:part name="parameters" element="tns:UpdateBudgetResponse" />
  </wsdl:message>
  <wsdl:portType name="ECologyServiceSoap">
    <wsdl:operation name="QueryBudget">
      <wsdl:input message="tns:QueryBudgetSoapIn" />
      <wsdl:output message="tns:QueryBudgetSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="UpdateBudget">
      <wsdl:input message="tns:UpdateBudgetSoapIn" />
      <wsdl:output message="tns:UpdateBudgetSoapOut" />
    </wsdl:operation>
  </wsdl:portType>
  <wsdl:binding name="ECologyServiceSoap" type="tns:ECologyServiceSoap">
    <soap:binding transport="http://schemas.xmlsoap.org/soap/http" />
    <wsdl:operation name="QueryBudget">
      <soap:operation soapAction="http://tempuri.org/QueryBudget" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="UpdateBudget">
      <soap:operation soapAction="http://tempuri.org/UpdateBudget" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
  </wsdl:binding>
  <wsdl:binding name="ECologyServiceSoap12" type="tns:ECologyServiceSoap">
    <soap12:binding transport="http://schemas.xmlsoap.org/soap/http" />
    <wsdl:operation name="QueryBudget">
      <soap12:operation soapAction="http://tempuri.org/QueryBudget" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="UpdateBudget">
      <soap12:operation soapAction="http://tempuri.org/UpdateBudget" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
  </wsdl:binding>
  <wsdl:service name="ECologyService">
    <wsdl:port name="ECologyServiceSoap" binding="tns:ECologyServiceSoap">
      <soap:address location="http://172.20.70.218:2088/Service/ECologyService.asmx" />
    </wsdl:port>
    <wsdl:port name="ECologyServiceSoap12" binding="tns:ECologyServiceSoap12">
      <soap12:address location="http://172.20.70.218:2088/Service/ECologyService.asmx" />
    </wsdl:port>
  </wsdl:service>
</wsdl:definitions>