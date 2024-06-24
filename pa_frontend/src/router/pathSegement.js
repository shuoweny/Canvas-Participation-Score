const pathSegment = [
  {
    path: '/',
    name: 'Dashboard',
  },
  {
    path: '/upcoming',
    name: 'Upcoming',
  }, 
  {
    path: '/subjectOverview/:subjectName',
    name: 'SubjectOverview', 
  }, 
  {
    path: '/userAccess/:subjectName', 
    name: 'UserAccess', 
  },
  {
    path: '/test',
    name: 'Test',
  }
]

export default pathSegment;