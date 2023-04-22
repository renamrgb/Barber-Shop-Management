let eventGuid = 0;
let todayStr = new Date().toISOString().replace(/T.*$/, ""); // YYYY-MM-DD of today

export const INITIAL_EVENTS = [
  {
    id: createEventId(),
    title: "All-day event",
    start: todayStr,
  },
  {
    id: createEventId(),
    title: "Timed event",
    start: todayStr + "T12:00:00",
  },
  {
    title: "Reunião",
    start: "2023-04-22T10:30:00",
    end: "2023-04-22T12:00:00",
    backgroundColor: "#378006",
    borderColor: "#378006",
  },
];

export function createEventId() {
  return String(eventGuid++);
}
