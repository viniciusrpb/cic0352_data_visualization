// Carregar os dados do JSON
d3.json('dados.json').then(function(data) {
    // Configurações do gráfico
    const margin = { top: 50, right: 10, bottom: 10, left: 10 },
        width = 960 - margin.left - margin.right,
        height = 500 - margin.top - margin.bottom;

    const x = d3.scalePoint().range([0, width]).padding(1),
        y = {};

    const line = d3.line(),
        axis = d3.axisLeft();

    const svg = d3.select("#chart").append("svg")
        .attr("width", width + margin.left + margin.right)
        .attr("height", height + margin.top + margin.bottom)
        .append("g")
        .attr("transform", "translate(" + margin.left + "," + margin.top + ")");

    // Escala de cores para o atributo products_int
    const color = d3.scaleOrdinal(d3.schemeCategory10)
        .domain(data.map(d => d.products_int));

    // Mapeamento dos nomes dos eixos
    const axisMapping = {
        'Cultivation (CO2Eq/Kg-1)': 'cultivation1',
        'Cultivation Water (m3/kg-1)': 'cultivation2',
        'Processing (CO2Eq/Kg-1)': 'processing1',
        'Processing Water (m3/kg-1)': 'processing2',
        'Transportation<br>(CO2Eq/Kg-1/Per Kilometer)<br>': 'transportation1',
        'Transportation<br>Ton of Liters-1/Per Kilometer<br>': 'transportation2'
    };

    // Inverter o mapeamento para facilitar o uso
    const inverseAxisMapping = Object.keys(axisMapping).reduce((acc, key) => {
        acc[axisMapping[key]] = key;
        return acc;
    }, {});

    // Extrair as dimensões e criar uma escala y para cada uma
    const dimensions = Object.keys(data[0]).filter(function(d) {
        return d != "products" && d != "products_int" && (y[d] = d3.scaleLinear()
            .domain(d3.extent(data, function(p) { return +p[d]; }))
            .range([height, 0]));
    });

    x.domain(dimensions);

    // Adicionar as linhas de fundo para uma melhor visualização
    svg.append("g")
        .attr("class", "background")
        .selectAll("path")
        .data(data)
        .enter().append("path")
        .attr("d", path);

    // Adicionar as linhas de frente para a interação
    svg.append("g")
        .attr("class", "foreground")
        .selectAll("path")
        .data(data)
        .enter().append("path")
        .attr("d", path)
        .style("stroke", d => color(d.products_int));

    // Adicionar um grupo de eixos para cada dimensão
    const g = svg.selectAll(".dimension")
        .data(dimensions)
        .enter().append("g")
        .attr("class", "dimension")
        .attr("transform", function(d) { return "translate(" + x(d) + ")"; });

    g.append("g")
        .attr("class", "axis")
        .each(function(d) { d3.select(this).call(axis.scale(y[d])); })
        .append("text")
        .style("text-anchor", "middle")
        .attr("y", -20)
        .text(function(d) { return inverseAxisMapping[d]; })
        .style("font-size", "12px")
        .style("fill", "black");

    // Função para desenhar as linhas
    function path(d) {
        return line(dimensions.map(function(p) { return [x(p), y[p](d[p])]; }));
    }
});
