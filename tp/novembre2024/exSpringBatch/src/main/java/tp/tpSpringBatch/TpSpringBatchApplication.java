package tp.tpSpringBatch;

import org.mycontrib.basic.comp.BasicPrefixeur;
import org.mycontrib.basic.comp.Prefixeur;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
//import tp.prefixeur.Prefixeur;
//import tp.prefixeur.PrefixeurConfig;

@SpringBootApplication(/*exclude = { DataSourceAutoConfiguration.class }*/)
//@Import(PrefixeurConfig.class)
public class TpSpringBatchApplication  implements CommandLineRunner{



	private final JobLauncher jobLauncher;
	private final ApplicationContext applicationContext;
	private Prefixeur prefixeur;
	
	@Autowired
	public TpSpringBatchApplication(JobLauncher jobLauncher,
			                          ApplicationContext applicationContext,
									Prefixeur prefixeur)  {
		//injection by constructor  
	    this.jobLauncher = jobLauncher;
	    this.applicationContext = applicationContext;
		this.prefixeur=prefixeur;
	}

	public static void main(String[] args) {
		//String defaultProfils  = "xmlJobConfig";
		String defaultProfils  = "";
		System.setProperty("spring.profiles.default", defaultProfils);
		SpringApplication.run(TpSpringBatchApplication.class, args);
	}
	
	@Override //from CommandLineRunner interface (called automatically)
	public void run(String... args) throws Exception {
		//String defaultJobName="myHelloWorldJob";
		//String defaultJobName="fromCsvToConsoleJob";
		String defaultJobName="fromCsvToXmlJob";
		//String defaultJobName="fromDbExtractStatToCsvJob";
		//String defaultJobName="generateDbDataSetJob";
		String jobName = null;
		if(args.length>0)
			jobName=args[0];
		else
		    jobName=System.getProperty("jobName", defaultJobName);
		
		String defaultInputFilePath="data/input/csv/products.csv";
		//String defaultInputFilePath="data/input/csv/newDetailsProducts_withOrWithoutErrors.csv";
		String inputFilePath=System.getProperty("inputFilePath", defaultInputFilePath);
		
		String defaultOutputFilePath="data/output/xml/products.xml";
		String outputFilePath=System.getProperty("outputFilePath", defaultOutputFilePath);
		
		System.out.println(prefixeur.prefixer(">>> jobName="+jobName +  " inputFilePath=" + inputFilePath + " outputFilePath=" + outputFilePath));
		
		Job job = (Job) applicationContext.getBean(jobName);
	 
	 JobParameters jobParameters = new JobParametersBuilder()
			 .addLong("timeStampOfJobInstance", System.currentTimeMillis())//Necessary for running several instances of a same job (each jobInstance must have a parameter that changes)
			 .addString("inputFilePath", inputFilePath)//used by some Reader/Writer
			 .addString("outputFilePath", outputFilePath)//used by some Reader/Writer
		     .addString("enableUpperCase", "true")//used by SimpleUppercaseProductProcessor
			 .toJobParameters();
	 var jobExecution = jobLauncher.run(job, jobParameters);

	 var batchStatus = jobExecution.getStatus();
	 while (batchStatus.isRunning()) {
	      System.out.println("Job still running...");
	      Thread.sleep(5000L);
	    }
	   
	 System.out.println("Job is finished ...");
	 }
}
