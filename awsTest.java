import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.lambda.invoke.LambdaInvokerFactory;
import com.amazonaws.AmazonClientException;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.DescribeInstancesRequest;
import com.amazonaws.services.ec2.model.Reservation;
import java.util.*;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.ec2.model.DescribeInstancesResult;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.DryRunResult;
import com.amazonaws.services.ec2.model.StartInstancesRequest;
import java.lang.NullPointerException;
import java.lang.*;
import com.amazonaws.services.ec2.model.DryRunSupportedRequest;
import com.amazonaws.services.ec2.model.StartInstancesRequest;
import com.amazonaws.services.ec2.model.StopInstancesRequest;
import com.amazonaws.services.ec2.model.InstanceType;
import com.amazonaws.services.ec2.model.RunInstancesRequest;
import com.amazonaws.services.ec2.model.RunInstancesResult;
import com.amazonaws.services.ec2.model.Tag;
import com.amazonaws.services.ec2.model.CreateTagsRequest;
import com.amazonaws.services.ec2.model.CreateTagsResult;
import com.amazonaws.services.ec2.model.DescribeRegionsResult;
import com.amazonaws.services.ec2.model.Region;
import com.amazonaws.services.ec2.model.AvailabilityZone;
import com.amazonaws.services.ec2.model.DescribeAvailabilityZonesResult;
import com.amazonaws.services.ec2.model.DescribeRegionsResult;
import com.amazonaws.services.ec2.model.Region;
public class awsTest {
	/*
	 * Cloud Computing, Data Computing Laboratory
	 * Department of Computer Science
	 * Chungbuk National University
	 */
	static AmazonEC2 ec2;
	private static void init() throws Exception {
		/*
		 * The ProfileCredentialsProvider will return your [default]
		 * credential profile by reading from the credentials file located at
		 * (~/.aws/credentials).
		 */
		ProfileCredentialsProvider credentialsProvider = new ProfileCredentialsProvider();
		try {
			credentialsProvider.getCredentials();
		} catch (Exception e) {
			throw new AmazonClientException(
					"Cannot load the credentials from the credential profiles file. " +
					"Please make sure that your credentials file is at the correct " +
					"location (~/.aws/credentials), and is in valid format.",
					e);

		}
		ec2 = AmazonEC2ClientBuilder.standard()
			.withCredentials(credentialsProvider)
			.withRegion("us-east-2") /* check the region at AWS console */
			.build();
	}

	public static void main(String[] args) throws Exception {
		init();
		Scanner menu = new Scanner(System.in);
		Scanner id_string = new Scanner(System.in);
		int number = 0;
		boolean loop=true;
		String id="0";
		while(loop)
		{
			System.out.println(" ");
			System.out.println(" ");
			System.out.println("------------------------------------------------------------");
			System.out.println(" Amazon AWS Control Panel using SDK ");
			System.out.println(" ");
			System.out.println(" Cloud Computing, Computer Science Department ");
			System.out.println(" at Chungbuk National University ");
			System.out.println("------------------------------------------------------------");
			System.out.println(" 1. list instance 2. available zones ");
			System.out.println(" 3. start instance 4. available regions ");
			System.out.println(" 5. stop instance 6. create instance ");
			System.out.println(" 7. reboot instance 8. list images ");
			System.out.println(" 99. quit ");
			System.out.println("------------------------------------------------------------");
			System.out.print("Enter an integer: ");
			number=menu.nextInt();
			switch(number) {
				case 1:

					listInstances();
					break;

				case 2:
					listZones();
					break;
				case 3:

					System.out.println("Enter the instance id: ");
					id= id_string.nextLine();
					startInstance(id);
					break;

				case 4:
					listRegions();
					break;

				case 5:

					System.out.println("Enter the instance id: ");
					id= id_string.nextLine();
					stopInstance(id);
					break;

				case 6:
					System.out.print("Enter the ami id: ");
					createInstance();
					break;

				case 99:
					System.out.println("end the Amazon AWS Control");
					loop=false;
					break;

				default:

					System.out.println("Please enter right number of menu");
					break;
			}
		}
	}
	public static void listInstances(){
		System.out.println("");
		System.out.println("Listing instances....");
		boolean done = false;
		DescribeInstancesRequest request = new DescribeInstancesRequest();
		while(!done) {
			DescribeInstancesResult response = ec2.describeInstances(request);
			for(Reservation reservation : response.getReservations()) {
				for(Instance instance : reservation.getInstances()) {
					System.out.printf(
							"[id] %s, " +
							"[AMI] %s, " +
							"[type] %s, " +
							"[state] %10s, " +
							"[monitoring state] %s",
							instance.getInstanceId(),
							instance.getImageId(),
							instance.getInstanceType(),
							instance.getState().getName(),
							instance.getMonitoring().getState());
				}
				System.out.println();
			}
			request.setNextToken(response.getNextToken());
			if(response.getNextToken() == null) {
				done = true;
			}
		}
	}

	public static void startInstance(String instance_name){ //start instance
		final AmazonEC2 new_ec2= AmazonEC2ClientBuilder.defaultClient();


		System.out.printf(
				"Starting .... %s", instance_name);

		DryRunSupportedRequest<StartInstancesRequest> dry_request =
			() -> {
				StartInstancesRequest request = new StartInstancesRequest()
					.withInstanceIds(instance_name);

				return request.getDryRunRequest();
			};
		// the original request of the dry run
		DryRunResult dry_response = ec2.dryRun(dry_request);

		if(!dry_response.isSuccessful()) {// if dry-run was not succeessful
			System.out.printf(
					"Failed dry run to start instance %s", instance_name);

			throw dry_response.getDryRunResponse(); 
		}//        //start instance that user put id 

		StartInstancesRequest request = new StartInstancesRequest()
			.withInstanceIds(instance_name);

		ec2.startInstances(request);

		System.out.printf("Successfully started instance %s", instance_name);
	}	

	public static void stopInstance(String instance_name){//stop instance

		final AmazonEC2 ec2 = AmazonEC2ClientBuilder.defaultClient();

		DryRunSupportedRequest<StopInstancesRequest> dry_request =
			() -> {
				StopInstancesRequest request = new StopInstancesRequest()
					.withInstanceIds(instance_name);

				return request.getDryRunRequest();
			};

		DryRunResult dry_response = ec2.dryRun(dry_request);

		if(!dry_response.isSuccessful()) {
			System.out.printf(
					"Failed dry run to stop instance %s", instance_name);
			throw dry_response.getDryRunResponse();
		}

		StopInstancesRequest request = new StopInstancesRequest()
			.withInstanceIds(instance_name);

		ec2.stopInstances(request);

		System.out.printf("Successfully stop instance %s", instance_name);


	}

	public static void createInstance(){ //create instance;
		/*	Scanner id_string = new Scanner(System.in);
			String ami_id;
			String reservation_id="0";
			ami_id= id_string.nextLine();
			final AmazonEC2 ec2 = AmazonEC2ClientBuilder.defaultClient();

			RunInstancesRequest run_request = new RunInstancesRequest()
			.withImageId(ami_id)
			.withInstanceType(InstanceType.T2Micro)
			.withMaxCount(1)
			.withMinCount(1);

			RunInstancesResult run_response = ec2.runInstances(run_request);

			reservation_id = run_response.getReservation().getInstances().get(0).getInstanceId();

		//	throw new AmazonClientException(
		//	"fail to create instance",e);
		//	System.out.println("error");


		// RunInstancesResult run_response = ec2.runInstances(run_request);
		//
		//       reservation_id = run_response.getReservation().getInstances().get(0).getInstanceId();

		System.out.printf(
		"Successfully started EC2 instance %s based on AMI %s", reservation_id, ami_id);

		 */	}

	public static void listZones(){
		final AmazonEC2 ec2 = AmazonEC2ClientBuilder.defaultClient();

		System.out.println("Available zones..");
		DescribeAvailabilityZonesResult zones_response =
			ec2.describeAvailabilityZones();

		for(AvailabilityZone zone : zones_response.getAvailabilityZones()) {
			System.out.printf(
					"[id] 	%s ,"+
					"[region]	%s, "+
					"[zone]	%s",
					zone.getZoneId(),
					zone.getRegionName(),
					zone.getZoneName());
			System.out.println();    
		}
	}


	public static void listRegions(){

		final AmazonEC2 ec2 = AmazonEC2ClientBuilder.defaultClient();
		DescribeRegionsResult regions_response = ec2.describeRegions();
		System.out.println("Available regions ....");
		for(Region region : regions_response.getRegions()) {
			System.out.printf(
					"[region] %15s,	" +
					"[endpoint] %10s",
					region.getRegionName(),
					region.getEndpoint());
			System.out.println();

		}

	}
}
