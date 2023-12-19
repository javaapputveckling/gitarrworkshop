


var calendarInstanceMonthly = new calendarJs( "calendarMonthly", {
    manualEditingEnabled: false,
    showExtraTitleBarButtons: false,
} );

var calendarInstanceWeekly = new calendarJs("calendarWeekly", {
    manualEditingEnabled: true,
    visibleDays: [0, 1, 2, 3, 4],
    workingDays: [0, 1, 2, 3, 4],
    viewToOpenOnFirstLoad: "full-week",
    workingHoursStart: "10:00",
    workingHoursEnd: "18:00",
    showWeekendDays: false,
    weekendDays: [5,6],
    minutesBetweenSections: "60",
    defaultEventDuration: 60,
    useLocalStorageForEvents: true,
} );


var calendarInstanceWidget = new calendarJs( "calendarWidget", {
    manualEditingEnabled: true,
    isWidget: true,
} );

function deleteHours() {
    var hourParent = document.getElementsByClassName("hours")[1];
    var size = hourParent.childNodes.length;

    for (let i = size - 1; i >= 0; i--) {
        if (i < 10 || i > 18) {
            hourParent.removeChild(hourParent.childNodes[i]);
        }
    }
}
