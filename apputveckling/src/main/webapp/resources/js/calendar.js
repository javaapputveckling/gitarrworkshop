var calendarInstanceWeekly = new calendarJs("calendarWeekly", {
    manualEditingEnabled: true,
    visibleDays: [0, 1, 2, 3, 4],
    workingDays: [0, 1, 2, 3, 4],
    viewToOpenOnFirstLoad: "full-week",
    workingHoursStart: "10:00",
    workingHoursEnd: "18:00",
    showWeekendDays: false,
    weekendDays: [5,6],
    minutesBetweenSections: 60,
    defaultEventDuration: 60,
    useLocalStorageForEvents: true,
    showExtraTitleBarButtons:false,
}
);

function fetchEvents() {
    const apiURL = "http://localhost:8080/apputveckling-1.0-SNAPSHOT/calendar/appointments";

    return fetch(apiURL)
        .then(response => response.json())
        .then(data => {
            // You can process the data here if needed
            return data;
        })
        .catch(error => {
            console.error('Error fetching data:', error);
        });
}

// Example of using the fetched data in another function
function AddEvents() {
    fetchEvents().then(data => {
        
    });
}




