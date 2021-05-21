package com.apiuni.apiuni.modelo;


import io.swagger.v3.oas.annotations.media.Schema;

public class ErrorObject400 {

    @Schema(
            description = "Codigo de error de estado HTTP",
            example = "400"
    )
    private String errorCode;

    @Schema(
            description = "Error descripcion y detalles",
            example = "Petición o Solicitud Incorrecta"
    )
    private String errorDescription;
    
    
    

	public ErrorObject400(String errorCode, String errorDescription) {
		super();
		this.errorCode = errorCode;
		this.errorDescription = errorDescription;
	}
	

	public ErrorObject400() {
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