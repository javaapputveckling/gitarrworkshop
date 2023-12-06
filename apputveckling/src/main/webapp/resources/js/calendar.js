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
// var newevent = {id:"3", from:"2023-12-07T14:30:00Z", title:"reservation", to:"2023-12-07T15:30:00Z"};
// calendarInstanceWeekly.addEvent(newevent)
// calendarInstanceWeekly.addEvent({id:"1", from:"2023-12-07T16:30:00Z", title:"reservation", to:"2023-12-07T17:30:00Z"})
function fetchEvents() {
    const apiURL = "http://localhost:8080/apputveckling-1.0-SNAPSHOT/calendar/appointments";

    return fetch(apiURL)
        .then(response => response.json())
        .then(data => {
            // You can process the data here if needed
            for (let i = 0; i<data.length;i++){
                calendarInstanceWeekly.addEvent({id:data[i].id, from:data[i].from, title:data[i].title, to:"2024-01-01T15:30:00Z", color:"red"});
            }
            return data;
        })
        .catch(error => {
            console.error('Error fetching data:', error);
        });
}

//fetchEvents();
// Example of using the fetched data in another function





