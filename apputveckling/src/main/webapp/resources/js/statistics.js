$(window).resize(function(){
    drawStuff();
});
async function drawStuff() {
    const data = new google.visualization.DataTable();
    data.addColumn('string', 'Month');
    data.addColumn('number', 'income');
    await fetch("http://localhost:8080/apputveckling-1.0-SNAPSHOT/myservlet")
        .then(response => response.json())
        .then(fetchedData => {
            fetchedData.forEach((item) => {
                data.addRow([item.month, item.income]);
            });
        });
    var options = {

        legend: {position: 'none'},
        chart: {
            title: 'Monthly income',
            subtitle: 'in swedish kronor (SEK)'
        },
        axes: {
            x: {
                0: {side: 'top', label: 'Income'} // Top x-axis.
            }

        },
        bar: {groupWidth: "90%", response:true}
    };
    var chart = new google.charts.Bar(document.getElementById('chart-div'));
    // Convert the Classic options to Material options.
    chart.draw(data, google.charts.Bar.convertOptions(options));
}