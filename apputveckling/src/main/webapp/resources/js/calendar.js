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
    //useLocalStorageForEvents: true,
    showExtraTitleBarButtons:false,
        onEventAdded: function(event) {
            // Create a new appointment object
            const newAppointment = {
                id: event.id,
                title: event.title,
                from: new Date(event.from).toISOString().substring(0, 19),
                to: new Date(event.to).toISOString().substring(0, 19),
                color: event.color,
                description: event.description,
                clientId: 1,
                productId: 1,
                // Add any other properties required by your backend
            };

            // Send a POST request to the backend
            fetch('/apputveckling-1.0-SNAPSHOT/calendar/appointments', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(newAppointment)
            }).then(response => {
                if (response.ok) {
                    console.log('Appointment created successfully');
                } else {
                    console.error('Error creating appointment:', response.statusText);
                    console.log((event.from).toISOString().substring(0, 19));
                }
            }).catch(error => {
                console.error('Error creating appointment:', error);
            });
        }
}
);
// var newevent = {id:"3", from:"2023-12-07T14:30:00Z", title:"reservation", to:"2023-12-07T15:30:00Z"};
// calendarInstanceWeekly.addEvent(newevent)
// calendarInstanceWeekly.addEvent({id:"1", from:"2023-12-07T16:30:00Z", title:"reservation", to:"2023-12-07T17:30:00Z"})
/*function fetchEvents() {
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

// Fetch events from the API and add them to the calendar
fetch('/apputveckling-1.0-SNAPSHOT/calendar/appointments')
    .then(response => response.json())
    .then(data => {
        // Loop through the array of objects in the 'events' array
        for (let i = 0; i < data.events.length; i++) {
            // Add each object as an event to the calendar
            calendarInstanceWeekly.addEvent(data.events[i]);
        }
    })
    .catch((error) => {
        console.error('Error:', error);
    });*/

fetch('/apputveckling-1.0-SNAPSHOT/calendar/appointments')
    .then(response => response.json())
    .then(data => {
        // Convert the 'events' array back to a JSON string
        let eventsJson = JSON.stringify(data.events);

        // Add the events to the calendar
        calendarInstanceWeekly.addEventsFromJson(eventsJson);
    })
    .catch((error) => {
        console.error('Error:', error);
    });

