package br.org.serratec.backend.dto;

import br.org.serratec.backend.model.Endereco;

public class EnderecoDTO {
	
	private Long id;
	private String cep;
	private String logradouro;
	private String complemento;
	private String numero;
	private String bairro;
	private String localidade;
	private String uf;
	
	
	public EnderecoDTO() {
		// TODO Auto-generated constructor stub
	}

		
	public EnderecoDTO(Endereco endereco) {
		super();
		this.id = endereco.getId();
		this.cep = endereco.getCep();
		this.logradouro = endereco.getRua();
		this.complemento = endereco.getComplemento();
		this.numero = endereco.getNumero();
		this.bairro = endereco.getBairro();
		this.localidade = endereco.getCidade();
		this.uf = endereco.getEstado();
		
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getCep() {
		return cep;
	}


	public void setCep(String cep) {
		this.cep = cep;
	}


	public String getLogradouro() {
		return logradouro;
	}


	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}


	public String getComplemento() {
		return complemento;
	}


	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}


	public String getNumero() {
		return numero;
	}


	public void setNumero(String numero) {
		this.numero = numero;
	}


	public String getBairro() {
		return bairro;
	}


	public void setBairro(String bairro) {
		this.bairro = bairro;
	}


	public String getLocalidade() {
		return localidade;
	}


	public void setLocalidade(String localidade) {
		this.localidade = localidade;
	}


	public String getUf() {
		return uf;
	}


	public void setUf(String uf) {
		this.uf = uf;
	}



}