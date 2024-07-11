// Define as dimensões e margens do gráfico
const margin = { top: 30, right: 10, bottom: 10, left: 10 },
      width = 960 - margin.left - margin.right,
      height = 500 - margin.top - margin.bottom;

// Anexe o objeto SVG ao corpo da página
const svg = d3.select("body").append("svg")
    .attr("width", width + margin.left + margin.right)
    .attr("height", height + margin.top + margin.bottom)
  .append("g")
    .attr("transform", "translate(" + margin.left + "," + margin.top + ")");

// Carregar os dados do arquivo JSON
d3.json("dados.json").then(function(data) {
    // Extrair a lista de dimensões que queremos plotar
    const dimensions = d3.keys(data[0]);

    // Crie uma escala para cada dimensão
    const y = {};
    for (let i in dimensions) {
        let name = dimensions[i];
        y[name] = d3.scaleLinear()
            .domain(d3.extent(data, d => +d[name]))
            .range([height, 0]);
    }

    // Crie uma escala x
    const x = d3.scalePoint()
        .range([0, width])
        .padding(1)
        .domain(dimensions);

    // Função para desenhar o caminho
    function path(d) {
        return d3.line()(dimensions.map(p => [x(p), y[p](d[p])]));
    }

    // Adicionar linhas de fundo cinzas para contexto
    svg.append("g")
        .attr("class", "background")
      .selectAll("path")
        .data(data)
      .enter().append("path")
        .attr("d", path);

    // Adicionar as linhas de primeiro plano
    const color = d3.scaleOrdinal(d3.schemeCategory10);

    svg.append("g")
        .attr("class", "foreground")
      .selectAll("path")
        .data(data)
      .enter().append("path")
        .attr("d", path)
        .style("stroke", d => color(d.products_int));

    // Adicionar um elemento de grupo para cada dimensão
    const g = svg.selectAll(".dimension")
        .data(dimensions)
      .enter().append("g")
        .attr("class", "dimension")
        .attr("transform", d => "translate(" + x(d) + ")");

    // Adicionar um eixo e título
    g.append("g")
        .attr("class", "axis")
        .each(function(d) {
            d3.select(this).call(d3.axisLeft(y[d]));
        })
      .append("text")
        .style("text-anchor", "middle")
        .attr("y", -9)
        .text(d => d);

    // Permitir brushing
    g.append("g")
        .attr("class", "brush")
        .each(function(d) {
            d3.select(this).call(d3.brushY()
                .extent([[-10,0], [10,height]])
                .on("start brush", brush)
            );
        });

    // Destaque as dimensões selecionadas
    function brush() {
        const actives = [];
        svg.selectAll(".brush")
            .filter(function(d) {
                y[d].brushSelectionValue = d3.brushSelection(this);
                return d3.brushSelection(this);
            })
            .each(function(d) {
                actives.push({
                    dimension: d,
                    extent: d3.brushSelection(this)
                });
            });

        // Defina a exibição das linhas de primeiro plano
        svg.selectAll(".foreground path").style("display", function(d) {
            return actives.every(function(active) {
                const dim = active.dimension;
                return active.extent[0] <= y[dim](d[dim]) && y[dim](d[dim]) <= active.extent[1];
            }) ? null : "none";
        });
    }
});
