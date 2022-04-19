package com.example.mercadillosmadridmapa.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResultList {

    public ResultList()
    {
        this.lista_resultados = new ArrayList<>();
    }

    @JsonProperty("@graph")
    private List<PointOfInterest> lista_resultados;

    public List<PointOfInterest> getLista_resultados() {
        return lista_resultados;
    }

    public void setLista_resultados(List<PointOfInterest> lista_resultados) {
        this.lista_resultados = lista_resultados;
    }
}
