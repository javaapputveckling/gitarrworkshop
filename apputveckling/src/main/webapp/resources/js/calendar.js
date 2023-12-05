



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

function getEvents(){
    const apiURL = "http://localhost:8080/apputveckling-1.0-SNAPSHOT/calendar/appointments"


// Make a GET request
    fetch(apiURL)
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            console.log(data);
        })
        .catch(error => {
            console.error('Error:', error);
        });

}


