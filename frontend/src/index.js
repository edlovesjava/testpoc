import React from 'react';
import ReactDOM from 'react-dom';
import moment from 'moment'

import EventService from './event-service';

import './index.css';

const eventService = new EventService();


class EventRow extends React.Component {
   render () {
        return (
            <tr className="events-table-header">
               <td>{this.props.event.name}</td>
               <td>{ moment(this.props.event.date).format('MMMM D h:mm A') }</td>
               <td>{this.props.event.venue.name}</td>
               <td>{this.props.event.venue.city}</td>
               <td>{this.props.event.venue.state}</td>
           </tr>
        )
   }
}

class EventsTable extends React.Component {

    render() {

        return (
             <table className="table events-table">
                <thead>
                <tr className="events-table-header">
                    <th>Event Name</th>
                    <th>Date</th>
                    <th>Venue</th>
                    <th>City</th>
                    <th>State</th>
                </tr>
                </thead>
                <tbody>
                {this.props.events.map((event,i) =>
                    <EventRow event={event} key={i} />
                )}
                </tbody>
            </table>
        );
    }

}

class AddEventModal extends React.Component {

    update () {
        let event = {
            name: this.refs.eventName.value,
            date: moment(this.refs.eventDate.value + ' ' + this.refs.eventTime.value),
            venue: {
                name: this.refs.venueName.value,
                city: this.refs.venueCity.value,
                state: this.refs.venueState.value
            }
        }
        this.props.source.add(event, (res)=>{console.log('added', res)}, (error) => {console.log('error ',error)})
    }

    render() {
        return (
            <div>
               <button id="add-btn" className="btn btn-primary" data-toggle="modal" data-target="#add-Event-Modal">Add Event</button>

               <div className="modal fade" id="add-Event-Modal">
                  <div className="modal-dialog">
                    <div className="modal-content">
                      <div className="modal-header">
                        <button type="button" className="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 className="modal-title">Add New Event</h4>
                      </div>
                      <div className="modal-body">
                        <form name="addEventForm" ref='myForm'>
                            <p>Event Information</p>
                            <input ref='eventName' placeholder="Event Name" />
                            <input type='date' ref='eventDate' placeholder="Date" id="add-event-date" />
                            <input type='time' ref='eventTime' placeholder="Time" id="add-event-time" />
                            <p>Venue Information</p>
                            <input ref = 'venueName' placeholder="Venue Name" />
                            <input ref = 'venueCity' placeholder="Venue City" />
                            <select ref = 'venueState' name="state" size="1">
                                  <option value="AK">AK</option>
                                  <option value="AL">AL</option>
                                  <option value="AR">AR</option>
                                  <option value="AZ">AZ</option>
                                  <option value="CA">CA</option>
                                  <option value="CO">CO</option>
                                  <option value="CT">CT</option>
                                  <option value="DC">DC</option>
                                  <option value="DE">DE</option>
                                  <option value="FL">FL</option>
                                  <option value="GA">GA</option>
                                  <option value="HI">HI</option>
                                  <option value="IA">IA</option>
                                  <option value="ID">ID</option>
                                  <option value="IL">IL</option>
                                  <option value="IN">IN</option>
                                  <option value="KS">KS</option>
                                  <option value="KY">KY</option>
                                  <option value="LA">LA</option>
                                  <option value="MA">MA</option>
                                  <option value="MD">MD</option>
                                  <option value="ME">ME</option>
                                  <option value="MI">MI</option>
                                  <option value="MN">MN</option>
                                  <option value="MO">MO</option>
                                  <option value="MS">MS</option>
                                  <option value="MT">MT</option>
                                  <option value="NC">NC</option>
                                  <option value="ND">ND</option>
                                  <option value="NE">NE</option>
                                  <option value="NH">NH</option>
                                  <option value="NJ">NJ</option>
                                  <option value="NM">NM</option>
                                  <option value="NV">NV</option>
                                  <option value="NY">NY</option>
                                  <option value="OH">OH</option>
                                  <option value="OK">OK</option>
                                  <option value="OR">OR</option>
                                  <option value="PA">PA</option>
                                  <option value="RI">RI</option>
                                  <option value="SC">SC</option>
                                  <option value="SD">SD</option>
                                  <option value="TN">TN</option>
                                  <option value="TX">TX</option>
                                  <option value="UT">UT</option>
                                  <option value="VA">VA</option>
                                  <option value="VT">VT</option>
                                  <option value="WA">WA</option>
                                  <option value="WI">WI</option>
                                  <option value="WV">WV</option>
                                  <option value="WY">WY</option>
                            </select>
                            <div className="modal-footer">
                                <button type="button" data-dismiss="modal">Close</button>
                                <button type="submit" onClick={this.update.bind(this)}>Add Event</button>
                            </div>
                        </form>
                      </div>
                    </div>
                  </div>
                </div>
            </div>
        )
    }
}

class App extends React.Component {
    constructor(props) {
        super(props);
        this.state = {events : []}
    }

    componentDidMount() {
        this.loadAll();

    }

    loadAll() {
        this.props.source.all(events => {
            this.setState({events: events});
        })
    }

    onUpdate (data) {
    console.log('on update fired')
    this.loadAll()
     }

    render() {
        return (<div>
            <EventsTable events={this.state.events} source={this.props.source} />
            <AddEventModal onUpdate={this.onUpdate.bind(this)} source={this.props.source} />


        </div>
    )}
}

// ========================================

//let events = eventService.all();
ReactDOM.render(

  <App source={eventService}/>,
  document.getElementById('root')
);
