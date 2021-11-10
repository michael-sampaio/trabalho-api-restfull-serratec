package br.org.serratec.backend.dto;

import java.time.LocalDate;

import javax.persistence.Embeddable;

import br.org.serratec.backend.model.Categoria;
import br.org.serratec.backend.model.Produto;

@Embeddable
public class AlterarProdutoDTO {
	
	private String nome;
	private String descricao;
	private Integer qtdEstoque;
	private LocalDate dataCadastro;
	private Double valorUnitario;
	private Categoria categoria;

	public AlterarProdutoDTO() {
		// TODO Auto-generated constructor stub
	}

	public AlterarProdutoDTO(Produto produto) {
		super();
		this.nome = produto.getNome();
		this.descricao = produto.getDescricao();
		this.qtdEstoque = produto.getQtdEstoque();
		this.dataCadastro = produto.getDataCadastro();
		this.valorUnitario = produto.getValorUnitario();
		this.categoria = produto.getCategoria();
	}

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getQtdEstoque() {
        return this.qtdEstoque;
    }

    public void setQtdEstoque(Integer qtdEstoque) {
        this.qtdEstoque = qtdEstoque;
    }

    public LocalDate getDataCadastro() {
        return this.dataCadastro;
    }

    public void setDataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public Double getValorUnitario() {
        return this.valorUnitario;
    }

    public void setValorUnitario(Double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public Categoria getCategoria() {
        return this.categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
	
}