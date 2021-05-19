package com.apiuni.apiuni.modelo;


import io.swagger.v3.oas.annotations.media.Schema;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;

//@Data
//@AllArgsConstructor
//@NoArgsConstructor
public class ErrorObject409 {

    @Schema(
            description = "Codigo de error de estado HTTP",
            example = "409"
    )
    private String errorCode;

    @Schema(
            description = "Error descripcion y detalles",
            example = "El objeto ya existe en la base de datos"
    )
    private String errorDescription;
    
    
    

	public ErrorObject409(String errorCode, String errorDescription) {
		super();
		this.errorCode = errorCode;
		this.errorDescription = errorDescription;
	}
	

	public ErrorObject409() {
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