const chartCanvas = document.getElementById("emissionsChart");

if (!chartCanvas || typeof Chart === "undefined" || !Array.isArray(emissionHistory)) {
    // Chart cannot be initialized.
} else {
    const records = emissionHistory.filter(record =>
        record &&
        record.year != null &&
        record.emissionsKt != null &&
        Number.isFinite(Number(record.year)) &&
        Number.isFinite(Number(record.emissionsKt))
    );

    if (records.length > 0) {
        const years = records.map(record => record.year);
        const emissions = records.map(record => Number(record.emissionsKt));

        new Chart(chartCanvas, {
            type: "line",
            data: {
                labels: years,
                datasets: [{
                    label: "CO₂ emissions (kt)",
                    data: emissions,
                    borderColor: "#176b50",
                    backgroundColor: "rgba(23, 107, 80, 0.10)",
                    borderWidth: 3,
                    pointRadius: 5,
                    pointHoverRadius: 7,
                    tension: 0.3,
                    fill: true
                }]
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
                plugins: {
                    legend: {
                        display: false
                    },
                    tooltip: {
                        callbacks: {
                            label: context =>
                                `${context.parsed.y.toLocaleString("en-US")} kt CO₂`
                        }
                    }
                },
                scales: {
                    x: {
                        title: {
                            display: true,
                            text: "Reporting year"
                        },
                        grid: {
                            display: false
                        }
                    },
                    y: {
                        beginAtZero: true,
                        title: {
                            display: true,
                            text: "CO₂ emissions (kt)"
                        },
                        ticks: {
                            callback: value => Number(value).toLocaleString("en-US")
                        }
                    }
                }
            }
        });
    }
}