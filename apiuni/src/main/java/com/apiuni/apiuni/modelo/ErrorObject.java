package com.apiuni.apiuni.modelo;


import io.swagger.v3.oas.annotations.media.Schema;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;

//@Data
//@AllArgsConstructor
//@NoArgsConstructor
public class ErrorObject {

    @Schema(
            description = "Codigo de error de estado HTTP",
            example = "400"
    )
    private String errorCode;

    @Schema(
            description = "Error descripcion y detalles",
            example = "El objeto solicitado no existe en la base de datos"
    )
    private String errorDescription;
    
    
    

	public ErrorObject(String errorCode, String errorDescription) {
		super();
		this.errorCode = errorCode;
		this.errorDescription = errorDescription;
	}
	

	public ErrorObject() {
		super();
	}


	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorDescription() {
		return errorDescription;
	}

	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}
    
    

}