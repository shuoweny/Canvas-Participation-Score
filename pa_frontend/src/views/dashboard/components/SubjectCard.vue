<template>
  <div class="subject-card">
    <div class="subject-background">
      <!-- <img class="subject-image" alt="subject_image" /> -->
    </div>
    <div class="subject-title">
      <div class="subject-name">
         <p>{{ subject.name }}</p>
      </div>
      <div class="subject-code">
        <p>{{ subject.course_code }}</p>
      </div>

    </div>
    
  </div>
</template>

<script>
export default {
  name: 'SubjectCard',
  props: {
    subject: {
      type: Object,
      required: true
    }
  }
}
</script>

<style lang="scss" scoped>
.subject-card {
  width: 20em;     
  height: 19em;    
  display: flex;
  flex-direction: column;
  align-items: center; 
  justify-content: center;
  border: 0px solid rgb(230, 230, 230);
  border-radius: 0.5em;
  background-color: rgb(250, 250, 250);
  box-shadow: 2px 2px 4px 4px rgb(200, 200, 200);
  margin-bottom: 2em;
  
  .subject-image {
    max-width: 100%;
    max-height: 100%;
    display: block;
    border-radius: 1em 1em 0em 0em;
  }

  transition: background-color 0.3s ease, box-shadow 0.3s ease; 

  &:hover {
    background-color: rgb(240, 240, 240); 
    box-shadow: 4px 4px 7px 7px rgb(180, 180, 180); 
  }
}

.subject-background {
  width: 100%;
  height: 55%;
  background-image: url("../../../assets/images/subject-default.jpg");
  border-radius: 0.5em 0.5em 0em 0em;
  background-size: 100% 100%;
}

.subject-title {
  width: 100%;
  height: 45%;
  text-align: left;
  p {
    margin-bottom: 0;
    margin-left: 0.5em;
    margin-top: 0.5em;
  }

}

.subject-name{
  font-size: 20px;
  font-weight: 600;
  color: rgb(37,53,73);

}

.subject-code{
  font-size: 15px;

}

</style>
