
package feilida.util.serviceNew;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="UpdateBudgetResult" type="{http://tempuri.org/}ArrayOfBudgetVO" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "updateBudgetResult"
})
@XmlRootElement(name = "UpdateBudgetResponse")
public class UpdateBudgetResponse {

    @XmlElement(name = "UpdateBudgetResult")
    protected ArrayOfBudgetVO updateBudgetResult;

    /**
     * Gets the value of the updateBudgetResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfBudgetVO }
     *     
     */
    public ArrayOfBudgetVO getUpdateBudgetResult() {
        return updateBudgetResult;
    }

    /**
     * Sets the value of the updateBudgetResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfBudgetVO }
     *     
     */
    public void setUpdateBudgetResult(ArrayOfBudgetVO value) {
        this.updateBudgetResult = value;
    }

}
